package shop.cazait.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.Role;

import shop.cazait.domain.auth.dto.*;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.master.service.MasterService;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.domain.user.service.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static shop.cazait.domain.auth.Role.USER;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserService userService;

    private final MasterService masterService;

    private final UserRepository userRepository;

    public UserAuthenticateOutDTO reIssueTokensByRole(Role exactRole, String accessToken, String refreshToken) throws MasterException, UserException {
        if (exactRole.equals(USER)) {
            return userService.reIssueTokens(accessToken, refreshToken);
        } else {
            return masterService.issueAccessToken(accessToken, refreshToken);
        }
    }

    public UserAuthenticateOutDTO logInByRole(Role exactRole, UserAuthenticateInDTO userAuthenticateInDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {
        if (exactRole.equals(USER)) {
            return userService.logIn(userAuthenticateInDTO);
        } else {
            return masterService.LoginMaster(userAuthenticateInDTO);
        }
    }
}




//    public KakaoToken getToken(final String code) {
//        try {
//            return client.getToken(new URI(kakaoAuthUrl), restapiKey, redirectUrl, code, "authorization_code");
//        } catch (Exception e) {
//            log.error("Something error..", e);
//            return KakaoToken.fail();
//        }
//    }
//
//    public KakaoInfo getInfo(final String code) {
//        final KakaoToken token = getToken(code);
//        log.debug("token = {}", token);
//        try {
//            return client.getInfo(new URI(kakaoUserApiUrl), token.getTokenType() + " " + token.getAccessToken());
//        } catch (Exception e) {
//            log.error("something error..", e);
//            return KakaoInfo.fail();
//        }
//    }


//    public void loginByKakao(String email) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        Optional<User> userFindByEmail = userRepository.findByEmail(email);
//        if(userFindByEmail.isEmpty()){
//            //회원가입
//            //enum type => kakao로 회원가입
//        }
//        else {
//            //카카오 계정일 때 => 자동 로그인
//            User user = userRepository.findByEmail(email).get();
//            PostLoginReq postLoginReq = new PostLoginReq(email,user.getPassword());
//            userService.logIn(postLoginReq);
//            //카카오 계정 x => 일반 로그인 실행하게끔 예외처리
//            //일반 로그인 시 kakao enum 으로 바뀜
//        }
//        }
//    }

