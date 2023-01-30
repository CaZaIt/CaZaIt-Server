package shop.cazait.domain.user.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.user.dto.*;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import shop.cazait.global.config.encrypt.AES128;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.Secret;
import shop.cazait.global.error.exception.BaseException;

import static shop.cazait.global.error.status.ErrorStatus.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public PostUserRes createUser(PostUserReq postUserReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

//        if(postUserReq.getEmail().isEmpty()){
//            throw new UserException(EMPTY_EMAIL);
//        }
//        if(postUserReq.getPassword().isEmpty()){
//            throw new UserException(EMPTY_PASSWORD);
//        }
//        if(postUserReq.getNickname().isEmpty()){
//            throw new UserException(EMPTY_NICKNAME);
//        }

        if(!userRepository.findByEmail(postUserReq.getEmail()).isEmpty()){
            throw new UserException(EXIST_EMAIL);
        }

        if(!userRepository.findByNickname(postUserReq.getNickname()).isEmpty()){
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

        if(userRepository.findByEmail(postLoginReq.getEmail()).isEmpty()){
            throw new UserException(FAILED_TO_LOGIN);
        }

        User user = postLoginReq.toEntity();
        User findUser = userRepository.findByEmail(user.getEmail()).get();
        String password;
        password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(findUser.getPassword());
        Long userIdx;
        if (password.equals(user.getPassword())) {
            userIdx = findUser.getId();
            String jwt = jwtService.createJwt(userIdx);
            String refreshToken = jwtService.createRefreshToken();
            User loginUser = User.builder()
                    .id(findUser.getId())
                    .email(findUser.getEmail())
                    .password(findUser.getPassword())
                    .nickname(findUser.getEmail())
                    .refreshToken(refreshToken)
                    .build();
            userRepository.save(loginUser);
            return PostLoginRes.of(findUser, jwt, refreshToken);
        }
        throw new UserException(FAILED_TO_LOGIN);
    }

    @Transactional(readOnly=true)
    public List<GetUserRes> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<GetUserRes> userResList = new ArrayList<>();
        for (User user : allUsers) {
            GetUserRes of = GetUserRes.of(user);
            userResList.add(of);
        }
        return userResList;
    }
    @Transactional(readOnly = true)
    public GetUserRes getUserByEmail (String email) throws UserException {
        if(userRepository.findByEmail(email).isEmpty()){
            throw new UserException(NOT_EXIST_USER);
        }
        User findUser = userRepository.findByEmail(email).get();
        return GetUserRes.of(findUser);
    }

    public PatchUserRes modifyUser(Long userIdx,PatchUserReq patchUserReq, String refreshToken){
        User modifyUser = patchUserReq.toEntity();
        User existUser = userRepository.findById(userIdx).get();

        existUser = User.builder()
                .id(userIdx)
                .email(modifyUser.getEmail())
                .password(modifyUser.getPassword())
                .nickname(modifyUser.getNickname())
                .refreshToken(refreshToken)
                .build();
        userRepository.save(existUser);
        return PatchUserRes.of(existUser);
    }

    public DeleteUserRes deleteUser(Long userIdx){
        User deleteUser = userRepository.findById(userIdx).get();
        userRepository.delete(deleteUser);
        return DeleteUserRes.of(deleteUser);
    }

    public PostLoginRes issueAccessToken(String accessToken,String refreshToken) throws BaseException , UserException{
        User user = null;
        Long userIdx = null;

        log.info("accessToken = " + accessToken);
        log.info("refreshToken = " + refreshToken);

        if(jwtService.isValidAccessToken(accessToken))
        {
            log.info("아직 accesstoken 유효");
            throw new UserException(FAILED_TO_LOGIN);
        }
        else
        {
            log.info("Access 토큰 만료됨");
            if(jwtService.isValidRefreshToken(refreshToken)){     //들어온 Refresh 토큰이 유효한지
                log.info("아직 refreshtoken 유효함");
                userIdx = jwtService.getUserIdx(accessToken);
                user = userRepository.findById(userIdx).get();
                String tokenFromDB = user.getRefreshToken();
                log.info("userIdx from accessToken: "+userIdx);
                log.info("refreshToken found by accessToken(userIdx): "+tokenFromDB);

                if(refreshToken.equals(tokenFromDB)) {
                    log.info("Access token 재발급");
                    accessToken = jwtService.createJwt(userIdx);
                }
                else{
                    log.error("Refresh Token Tampered, not equal from db refreshtoken");
                    throw new UserException(FAILED_TO_LOGIN);
                }
            }
            else
            {
                log.info("refresh token 재발급");
                userIdx = jwtService.getUserIdx(accessToken);
                user = userRepository.findById(userIdx).get();
                accessToken = jwtService.createJwt(userIdx);
                refreshToken = jwtService.createRefreshToken();
            }
        }
        return PostLoginRes.of(user,accessToken,refreshToken);
    }
}
