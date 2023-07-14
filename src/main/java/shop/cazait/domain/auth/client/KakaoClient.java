package shop.cazait.domain.auth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.auth.config.FeignConfiguration;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserInfoOutDTO;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserTokenOutDTO;

import java.net.URI;


@FeignClient(name="kakaoClient",configuration = FeignConfiguration.class)
public interface KakaoClient {

    @PostMapping
    ExtKakaoUserInfoOutDTO getInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @PostMapping
    ExtKakaoUserTokenOutDTO getToken(URI baseUrl, @RequestParam("client_id") String restApiKey,
                                     @RequestParam("redirect_uri") String redirectUrl,
                                     @RequestParam("code") String code,
                                     @RequestParam("grant_type") String grantType);
}
