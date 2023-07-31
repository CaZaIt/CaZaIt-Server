package shop.cazait.domain.user.service;


import static shop.cazait.global.error.status.ErrorStatus.*;
import static shop.cazait.global.error.status.SuccessStatus.SIGNUP_AVAILABLE;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.dto.UserAuthenticateInDTO;
import shop.cazait.domain.auth.dto.UserAuthenticateOutDTO;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${password-secret-key}")
    private String PASSWORD_SECRET_KEY;

    public UserCreateOutDTO createUser(UserCreateInDTO userCreateInDTO)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (userRepository.findByAccountName(userCreateInDTO.getAccountName()).isPresent()) {
            throw new UserException(EXIST_ACCOUNTNAME);
        }

        if (userRepository.findByPhoneNumber(userCreateInDTO.getPhoneNumber()).isPresent()) {
            throw new UserException(EXIST_PHONENUMBER);
        }

        if (userRepository.findByNickname(userCreateInDTO.getNickname()).isPresent()) {
            throw new UserException(EXIST_NICKNAME);
        }

        String encryptedPassword = encryptUserPassword(userCreateInDTO.getPassword());
        UserCreateInDTO encryptUserCreateInDTO = userCreateInDTO.encryptUserUpdateDTO(userCreateInDTO, encryptedPassword);

        User user = UserCreateInDTO.toEntity(encryptUserCreateInDTO);
        userRepository.save(user);

        return UserCreateOutDTO.of(user);
    }

    public UserAuthenticateOutDTO logInUser(UserAuthenticateInDTO userAuthenticateInDTO)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        User findUser = userRepository.findByAccountName(userAuthenticateInDTO.getAccountName())
                .orElseThrow(() -> new UserException(FAILED_TO_LOGIN));

        //DB에 있는 암호화된 비밀번호
        String findUserPassword = findUser.getPassword();
        //로그인 시 입력한 비밀번호를 암호화
        String loginPassword = encryptUserPassword(userAuthenticateInDTO.getPassword());

        if (findUserPassword.equals(loginPassword)) {
            UUID userIdx = findUser.getId();

            //토큰 발행
            String accessToken = jwtService.createJwt(userIdx);
            String refreshToken = jwtService.createRefreshToken();

            //refreshToken 추가하여 DB저장
            User loginUser = findUser.updateUserRefreshToken(refreshToken);
            userRepository.save(loginUser);

            return UserAuthenticateOutDTO.of(loginUser, accessToken);
        }
        throw new UserException(FAILED_TO_LOGIN);
    }

    @Transactional(readOnly = true)
    public List<UserFindOutDTO> getUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(UserFindOutDTO::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserFindOutDTO getUser(UUID userIdx) throws UserException {
        userRepository.findById(userIdx).orElseThrow(() -> new UserException(NOT_EXIST_USER));
        User findUser = userRepository.findById(userIdx).get();
        return UserFindOutDTO.of(findUser);
    }

    public UserUpdateOutDTO updateUserProfile(UUID userIdx, UserUpdateInDTO userUpdateInDTO) throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        User existUser = userRepository.findById(userIdx)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        //DTO의 비밀번호 암호화
        String encryptedPassword = encryptUserPassword(userUpdateInDTO.getPassword());
        UserUpdateInDTO encryptedUserUpdateDTO = userUpdateInDTO.encryptUserUpdateDTO(encryptedPassword);

        //유저 정보 수정
        User modifiedUser = existUser.updateUserProfile(encryptedUserUpdateDTO);
        userRepository.save(modifiedUser);
        return UserUpdateOutDTO.of(modifiedUser);
    }

    public UserDeleteOutDTO deleteUserById(UUID userIdx) throws UserException {
        userRepository.findById(userIdx).orElseThrow(() -> new UserException(NOT_EXIST_USER));

        User deleteUser = userRepository.findById(userIdx).get();
        userRepository.delete(deleteUser);
        return UserDeleteOutDTO.of(deleteUser);
    }

    public String encryptUserPassword(String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new AES128(PASSWORD_SECRET_KEY).encrypt(password);
    }

    public SuccessResponse<String> findUserDuplicateAccountName(UserFindDuplicateAccountNameInDTO userFindDuplicateAccountNameInDTO) throws UserException {
        String accountName = userFindDuplicateAccountNameInDTO.getAccountName();
        if (userRepository.findByAccountName(accountName).isPresent()) {
            throw new UserException(EXIST_ACCOUNTNAME);
        }
        return new SuccessResponse<>(SIGNUP_AVAILABLE, accountName);
    }

    public SuccessResponse<String> findUserDuplicateNickname(UserFindDuplicateNicknameInDTO userFindDuplicateNicknameInDTO) throws UserException {
        String nickname = userFindDuplicateNicknameInDTO.getNickname();
        if (userRepository.findByNickname(nickname.trim()).isPresent()) {
            throw new UserException(EXIST_NICKNAME);
        }

        return new SuccessResponse<>(SIGNUP_AVAILABLE, nickname);
    }


    public UserAuthenticateOutDTO reIssueTokens(String accessToken, String refreshToken) throws UserException {

        User user = null;
        UUID userIdx = jwtService.getUserIdx(accessToken);
        log.info("accessToken = " + accessToken);
        log.info("refreshToken = " + refreshToken);

        if (jwtService.isValidAccessTokenInRefresh(accessToken)) {
            log.info("아직 accesstoken 유효");
            throw new UserException(NOT_EXPIRED_TOKEN);
        } else {
            log.info("Access 토큰 만료됨");
            if (jwtService.isValidRefreshTokenInRefresh(refreshToken)) {     //들어온 Refresh 토큰이 유효한지
                log.info("아직 refreshtoken 유효함");

                if (isEqualRefreshTokenFromDB(accessToken, refreshToken)) {
                    log.info("Access token 재발급");
                    accessToken = jwtService.createJwt(userIdx);
                    user = userRepository.findById(userIdx).get();
                }
            } else {
                if (isEqualRefreshTokenFromDB(accessToken, refreshToken)) {
                    log.info("Access token 재발급");
                    accessToken = jwtService.createJwt(userIdx);

                    log.info("refresh token 재발급");
                    refreshToken = jwtService.createRefreshToken();
                    user = userRepository.findById(userIdx).get();

                    //새로 발급된 RefreshToken 업데이트 후 유저를 DB에 저장
                    user = user.updateUserRefreshToken(refreshToken);
                    userRepository.save(user);
                }
            }
        }
        return UserAuthenticateOutDTO.of(user, accessToken);
    }

    public boolean isEqualRefreshTokenFromDB(String accessToken, String refreshToken) throws UserException {
        UUID userIdx = jwtService.getUserIdx(accessToken);
        User user = userRepository.findById(userIdx).get();
        String tokenFromDB = user.getRefreshToken();
        log.info("userIdx from accessToken: " + userIdx);
        log.info("refreshToken found by accessToken(userIdx): " + tokenFromDB);

        if (refreshToken.equals(tokenFromDB)) {
            log.info("Access token 재발급");
            return true;
        } else {
            log.error("Refresh Token Tampered, not equal from db refreshtoken");
            throw new UserException(INVALID_JWT);
        }
    }

    public UserFindAccountNameOutDTO findUserAccountName(String phoneNumber) throws UserException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        return UserFindAccountNameOutDTO.of(user);
    }

    public UserEnterAccountNameInResetPasswordOutDTO verifyUserAccountNameInResetPassword(String accountName) throws UserException {
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));
        return UserEnterAccountNameInResetPasswordOutDTO.of(user);
    }

    public UserEnterPasswordInResetPasswordOutDTO updateUserPasswordInResetPassword(String phoneNumber, String password) throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));


        String encryptUserPassword = encryptUserPassword(password);
        if(user.getPassword().equals(encryptUserPassword)){
            throw new UserException(SAME_AS_CURRENT_PASSWORD);
        }
        //비밀번호 암호화

        User updatePasswordUser = user.updateUserPasswordInResetPassword(encryptUserPassword);
        userRepository.save(updatePasswordUser);

        return UserEnterPasswordInResetPasswordOutDTO.of(user,password);
    }
}
