package shop.cazait.domain.user.service;

import static shop.cazait.domain.auth.Role.USER;
import static shop.cazait.global.error.status.ErrorStatus.EXIST_EMAIL;
import static shop.cazait.global.error.status.ErrorStatus.EXIST_NICKNAME;
import static shop.cazait.global.error.status.ErrorStatus.FAILED_TO_LOGIN;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_JWT;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_USER;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXPIRED_TOKEN;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.dto.PostLoginReq;
import shop.cazait.domain.auth.dto.PostLoginRes;
import shop.cazait.domain.user.dto.DeleteUserRes;
import shop.cazait.domain.user.dto.GetUserRes;
import shop.cazait.domain.user.dto.PatchUserReq;
import shop.cazait.domain.user.dto.PatchUserRes;
import shop.cazait.domain.user.dto.PostUserReq;
import shop.cazait.domain.user.dto.PostUserRes;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.Secret;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public PostUserRes createUser(PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (!userRepository.findByEmail(postUserReq.getEmail()).isEmpty()) {
            throw new UserException(EXIST_EMAIL);
        }

        if (!userRepository.findByNickname(postUserReq.getNickname()).isEmpty()) {
            throw new UserException(EXIST_NICKNAME);
        }

        String pwd;
        pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());

        PostUserReq EncryptPostUserReq = new PostUserReq(postUserReq.getEmail(), pwd, postUserReq.getNickname());
        User user = EncryptPostUserReq.toEntity();
        userRepository.save(user);

        return PostUserRes.of(user);
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (userRepository.findByEmail(postLoginReq.getEmail()).isEmpty()) {
            throw new UserException(NOT_EXIST_USER);
        }

        User findUser = userRepository.findByEmail(postLoginReq.getEmail()).get();

        String password;
        password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(findUser.getPassword());

        Long userIdx;
        if (password.equals(postLoginReq.getPassword())) {
            userIdx = findUser.getId();

            String jwt = jwtService.createJwt(userIdx);
            String refreshToken = jwtService.createRefreshToken();

            User loginUser = User.builder()
                    .id(findUser.getId())
                    .email(findUser.getEmail())
                    .password(findUser.getPassword())
                    .nickname(findUser.getNickname())
                    .refreshToken(refreshToken)
                    .build();
            userRepository.save(loginUser);
            return PostLoginRes.of(findUser, jwt, refreshToken, USER);
        }
        throw new UserException(FAILED_TO_LOGIN);
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<GetUserRes> userListsRes = new ArrayList<>();

        for (User user : allUsers) {
            GetUserRes of = GetUserRes.of(user);
            userListsRes.add(of);
        }

        return userListsRes;
    }

    @Transactional(readOnly = true)
    public GetUserRes getUserInfo(Long userIdx) throws UserException {
        if (userRepository.findById(userIdx).isEmpty()) {
            throw new UserException(NOT_EXIST_USER);
        }
        User findUser = userRepository.findById(userIdx).get();
        return GetUserRes.of(findUser);
    }

    public PatchUserRes modifyUser(Long userIdx, PatchUserReq patchUserReq, String refreshToken) throws UserException {
        User modifyUser = patchUserReq.toEntity();
        if (userRepository.findById(userIdx).isEmpty()) {
            throw new UserException(NOT_EXIST_USER);
        }

        
        User existUser = User.builder()
                .id(userIdx)
                .email(modifyUser.getEmail())
                .password(modifyUser.getPassword())
                .nickname(modifyUser.getNickname())
                .refreshToken(refreshToken)
                .build();
        userRepository.save(existUser);
        return PatchUserRes.of(existUser);
    }

    public DeleteUserRes deleteUser(Long userIdx) throws UserException {
        if (userRepository.findById(userIdx).isEmpty()) {
            throw new UserException(NOT_EXIST_USER);
        }

        User deleteUser = userRepository.findById(userIdx).get();
        userRepository.delete(deleteUser);
        return DeleteUserRes.of(deleteUser);
    }

    public SuccessResponse<String> checkduplicateEmail(String email) throws UserException {
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new UserException(EXIST_EMAIL);
        }
        return new SuccessResponse(SUCCESS, "회원가입이 가능합니다.");
    }

    public SuccessResponse<String> checkduplicateNickname(String nickname) throws UserException {
        if (!userRepository.findByNickname(nickname).isEmpty()) {
            throw new UserException(EXIST_NICKNAME);
        }
        return new SuccessResponse(SUCCESS, "회원가입이 가능합니다.");
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

    public PostLoginRes reIssueTokens(String accessToken,String refreshToken, Long userIdx) throws UserException{

         User user = null;

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
        return PostLoginRes.of(user,accessToken,refreshToken,USER);
    }
    public boolean isEqualRefreshTokenFromDB(String accessToken, String refreshToken) throws UserException{
        Long userIdx = jwtService.getUserIdx(accessToken);
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