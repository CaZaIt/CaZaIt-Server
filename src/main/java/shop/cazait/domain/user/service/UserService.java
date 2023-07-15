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

        if (userRepository.findByAccountNumber(userCreateInDTO.getAccountNumber()).isPresent()) {
            throw new UserException(EXIST_ACCOUNTNUMBER);
        }

        if (userRepository.findByPhoneNumber(userCreateInDTO.getPhoneNumber()).isPresent()) {
            throw new UserException(EXIST_PHONENUMBER);
        }

        if (userRepository.findByNickname(userCreateInDTO.getNickname()).isPresent()) {
            throw new UserException(EXIST_NICKNAME);
        }

        String encryptedPassword = encryptPassword(userCreateInDTO.getPassword());
        UserCreateInDTO encryptUserCreateInDTO = UserCreateInDTO.encryptUserPassword(userCreateInDTO, encryptedPassword);

        User user = UserCreateInDTO.toEntity(encryptUserCreateInDTO);
        userRepository.save(user);

        return UserCreateOutDTO.of(user);
    }

    public UserAuthenticateOutDTO logIn(UserAuthenticateInDTO userAuthenticateInDTO)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        userRepository.findByAccountNumber(userAuthenticateInDTO.getAccountNumber()).orElseThrow(()->new UserException(FAILED_TO_LOGIN));

        User findUser = userRepository.findByAccountNumber(userAuthenticateInDTO.getAccountNumber()).get();

        String password;
        password = new AES128(PASSWORD_SECRET_KEY).decrypt(findUser.getPassword());

        UUID userIdx;
        if (password.equals(userAuthenticateInDTO.getPassword())) {
            userIdx = findUser.getId();

            String accessToken = jwtService.createJwt(userIdx);
            String refreshToken = jwtService.createRefreshToken();

            User loginUser = findUser.loginUser(refreshToken);
            userRepository.save(loginUser);
            return UserAuthenticateOutDTO.of(findUser, accessToken, refreshToken, "user");
        }
        throw new UserException(FAILED_TO_LOGIN);
    }

    @Transactional(readOnly = true)
    public List<UserFindOutDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return  allUsers.stream()
                .map(UserFindOutDTO::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserFindOutDTO getUserInfo(UUID userIdx) throws UserException {
        userRepository.findById(userIdx).orElseThrow(()->new UserException(NOT_EXIST_USER));
        User findUser = userRepository.findById(userIdx).get();
        return UserFindOutDTO.of(findUser);
    }

    public UserUpdateOutDTO modifyUser(UUID userIdx, UserUpdateInDTO userUpdateInDTO) throws UserException {

        userRepository.findById(userIdx).orElseThrow(()->new UserException(NOT_EXIST_USER));
        String refreshToken = userRepository.findById(userIdx).get().getRefreshToken();
        User modifyUser = User.updateUserProfile(userIdx, refreshToken, userUpdateInDTO);
        userRepository.save(modifyUser);
        return UserUpdateOutDTO.of(modifyUser);
    }

    public UserDeleteOutDTO deleteUser(UUID userIdx) throws UserException {
        userRepository.findById(userIdx).orElseThrow(()->new UserException(NOT_EXIST_USER));

        User deleteUser = userRepository.findById(userIdx).get();
        userRepository.delete(deleteUser);
        return UserDeleteOutDTO.of(deleteUser);
    }

    public String encryptPassword(String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new AES128(PASSWORD_SECRET_KEY).encrypt(password);
    }

    public SuccessResponse<String> checkduplicateaccountNumber(UserFindDuplicateAccountNumberInDTO userFindDuplicateAccountNumberInDTO) throws UserException {
        String accountNumber = userFindDuplicateAccountNumberInDTO.getAccountNumber();
        if (userRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new UserException(EXIST_ACCOUNTNUMBER);
        }
        return new SuccessResponse<>(SIGNUP_AVAILABLE, accountNumber);
    }

    public SuccessResponse<String> checkduplicateNickname(UserFindDuplicateNicknameInDTO userFindDuplicateNicknameInDTO) throws UserException {
        String nickname = userFindDuplicateNicknameInDTO.getNickname();
        if (userRepository.findByNickname(nickname.trim()).isPresent()) {
            throw new UserException(EXIST_NICKNAME);
        }

        return new SuccessResponse<>(SIGNUP_AVAILABLE, nickname);
    }


//    public PostLoginRes reIssueTokens(String accessToken,String refreshToken) throws UserException{
//
//        User user = null;
//        Long userIdx = null;
//
//        log.info("accessToken = " + accessToken);
//        log.info("refreshToken = " + refreshToken);
//
//        if(jwtService.isValidAccessTokenInRefresh(accessToken))
//        {
//            log.info("아직 accesstoken 유효");
//            throw new UserException(NOT_EXPIRED_TOKEN);
//        }
//        else
//        {
//            log.info("Access 토큰 만료됨");
//            if(jwtService.isValidRefreshTokenInRefresh(refreshToken)){     //들어온 Refresh 토큰이 유효한지
//                log.info("아직 refreshtoken 유효함");
//
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                String tokenFromDB = user.getRefreshToken();
//                log.info("userIdx from accessToken: "+userIdx);
//                log.info("refreshToken found by accessToken(userIdx): "+tokenFromDB);
//
//                if(refreshToken.equals(tokenFromDB)) {
//                    log.info("Access token 재발급");
//                    accessToken = jwtService.createJwt(userIdx);
//                }
//                else{
//                    log.error("Refresh Token Tampered, not equal from db refreshtoken");
//                    throw new UserException(INVALID_JWT);
//                }
//            }
//            else
//            {
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                String tokenFromDB = user.getRefreshToken();
//                if(refreshToken.equals(tokenFromDB)) {
//                    log.info("Access token 재발급");
//                    accessToken = jwtService.createJwt(userIdx);
//                }
//                else{
//                    log.error("Refresh Token Tampered, not equal from db refreshtoken");
//                    throw new UserException(INVALID_JWT);
//                }
//                log.info("refresh token 재발급");
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                accessToken = jwtService.createJwt(userIdx);
//                refreshToken = jwtService.createRefreshToken();
//            }
//        }
//        return PostLoginRes.of(user,accessToken,refreshToken,USER);
//    }

    public UserAuthenticateOutDTO reIssueTokens(String accessToken, String refreshToken) throws UserException{

        User user = null;
        UUID userIdx = jwtService.getUserIdx(accessToken);
        log.info("accessToken = " + accessToken);
        log.info("refreshToken = " + refreshToken);

        if(jwtService.isValidAccessTokenInRefresh(accessToken))
        {
            log.info("아직 accesstoken 유효");
            throw new UserException(NOT_EXPIRED_TOKEN);
        }
        else
        {
            log.info("Access 토큰 만료됨");
            if(jwtService.isValidRefreshTokenInRefresh(refreshToken)){     //들어온 Refresh 토큰이 유효한지
                log.info("아직 refreshtoken 유효함");

                if(isEqualRefreshTokenFromDB(accessToken, refreshToken)) {
                    log.info("Access token 재발급");
                    accessToken = jwtService.createJwt(userIdx);
                    user = userRepository.findById(userIdx).get();
                }
            }
            else
            {
                if(isEqualRefreshTokenFromDB(accessToken, refreshToken)) {
                    log.info("Access token 재발급");
                    accessToken = jwtService.createJwt(userIdx);

                    log.info("refresh token 재발급");
                    refreshToken = jwtService.createRefreshToken();
                    user = userRepository.findById(userIdx).get();
                }
            }
        }
        return UserAuthenticateOutDTO.of(user,accessToken,refreshToken,"user");
    }
    public boolean isEqualRefreshTokenFromDB(String accessToken, String refreshToken) throws UserException{
        UUID userIdx = jwtService.getUserIdx(accessToken);
        User user = userRepository.findById(userIdx).get();
        String tokenFromDB = user.getRefreshToken();
        log.info("userIdx from accessToken: "+userIdx);
        log.info("refreshToken found by accessToken(userIdx): "+tokenFromDB);

        if(refreshToken.equals(tokenFromDB)) {
            log.info("Access token 재발급");
            return true;
        }
        else{
            log.error("Refresh Token Tampered, not equal from db refreshtoken");
            throw new UserException(INVALID_JWT);
        }
    }

//    public PostLoginRes reIssueTokens(String accessToken, String refreshToken) throws UserException, ExpiredJwtException{
//
//        User user = null;
//        Long userIdx = null;
//
//        log.info("accessToken = " + accessToken);
//        log.info("refreshToken = " + refreshToken);
//
//        try {
//            boolean validAccessTokenInRefresh = jwtService.isValidAccessTokenInRefresh(accessToken);
//            if (validAccessTokenInRefresh) {
//                log.info("아직 accesstoken 유효");
//                throw new UserException(NOT_EXPIRED_TOKEN);
//            }
//        } catch (ExpiredJwtException exception) {
//            log.info("Access 토큰 만료됨");
//            try {
//                if (jwtService.isValidRefreshTokenInRefresh(refreshToken)) {
//                    log.info("아직 refreshtoken 유효함");
//                    userIdx = jwtService.getUserIdx(accessToken);
//                    user = userRepository.findById(userIdx).get();
//                    String tokenFromDB = user.getRefreshToken();
//                    log.info("userIdx from accessToken: " + userIdx);
//                    log.info("refreshToken found by accessToken(userIdx): " + tokenFromDB);
//
//                    if (refreshToken.equals(tokenFromDB)) {
//                        log.info("Access token 재발급");
//                        accessToken = jwtService.createJwt(userIdx);
//                    } else {
//                        log.error("Refresh Token Tampered, not equal from db refreshtoken");
//                        throw new UserException(INVALID_JWT);
//                    }
//                }
//            } catch (ExpiredJwtException Expiredexception) {
//                log.info("refresh token 재발급");
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                accessToken = jwtService.createJwt(userIdx);
//                refreshToken = jwtService.createRefreshToken();
//            }
//        }
//
//        return PostLoginRes.of(user, accessToken, refreshToken, USER);
//    }
//}

    }//        if(jwtService.isValidAccessTokenInRefresh(accessToken))
//        {
//            log.info("아직 accesstoken 유효");
//            throw new UserException(NOT_EXPIRED_TOKEN);
//        }
//        else
//        {
//            log.info("Access 토큰 만료됨");
//            if(jwtService.isValidRefreshTokenInRefresh(refreshToken)){     //들어온 Refresh 토큰이 유효한지
//                log.info("아직 refreshtoken 유효함");
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                String tokenFromDB = user.getRefreshToken();
//                log.info("userIdx from accessToken: "+userIdx);
//                log.info("refreshToken found by accessToken(userIdx): "+tokenFromDB);
//
//                if(refreshToken.equals(tokenFromDB)) {
//                    log.info("Access token 재발급");
//                    accessToken = jwtService.createJwt(userIdx);
//                }
//                else{
//                    log.error("Refresh Token Tampered, not equal from db refreshtoken");
//                    throw new UserException(INVALID_JWT);
//                }
//            }
//            else
//            {
//                log.info("refresh token 재발급");
//                userIdx = jwtService.getUserIdx(accessToken);
//                user = userRepository.findById(userIdx).get();
//                accessToken = jwtService.createJwt(userIdx);
//                refreshToken = jwtService.createRefreshToken();
//            }
//        }
//        return PostLoginRes.of(user,accessToken,refreshToken,USER);