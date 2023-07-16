package shop.cazait.domain.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.auth.client.KakaoClient;


import shop.cazait.domain.auth.dto.UserAuthenticateOutDTO;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserTokenOutDTO;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserInfoOutDTO;
import shop.cazait.domain.user.dto.UserCreateOutDTO;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.global.config.encrypt.JwtService;


import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Value("${rest-api-key}")
    private String restapiKey;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;

    private final String kakaoAuthUrl = "https://kauth.kakao.com/oauth/token";


    private final String kakaoUserApiUrl = "https://kapi.kakao.com/v2/user/me";
    private final KakaoClient client;

    public ExtKakaoUserTokenOutDTO getToken(final String code) {
        try {
            return client.getToken(new URI(kakaoAuthUrl), restapiKey, redirectUrl, code, "authorization_code");
        } catch (Exception e) {
            log.error("kakao Token error..", e);
            return ExtKakaoUserTokenOutDTO.fail();
        }
    }

    public ExtKakaoUserInfoOutDTO getInfo(final String code) {
        final ExtKakaoUserTokenOutDTO token = getToken(code);
        try {
            return client.getInfo(new URI(kakaoUserApiUrl), token.getTokenType() + " " + token.getAccessToken());
        } catch (Exception e) {
            log.error("kakao user info error", e);
            return ExtKakaoUserInfoOutDTO.fail();
        }
    }

    public UserCreateOutDTO kakaoSignUpnUser(Long kakaoId){
        User user = User.kakaoSignUpUser(kakaoId);
        User signUpUser = userRepository.save(user);
        return UserCreateOutDTO.of(signUpUser);
    }

    public UserAuthenticateOutDTO kakaoLoginUser(Long kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).get();
        UUID id = user.getId();

        String accessToken = jwtService.createJwt(id);
        String refreshToken = jwtService.createRefreshToken();

        User kakaoLoginUser = user.kakaoLoginUser(refreshToken);
        userRepository.save(kakaoLoginUser);

        return UserAuthenticateOutDTO.of(user, accessToken);
    }


}
