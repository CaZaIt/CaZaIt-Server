//package shop.cazait.domain.auth.client;
//
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//import shop.cazait.domain.auth.config.KakaoFeignConfiguration;
//import shop.cazait.domain.auth.dto.KakaoCode;
//import shop.cazait.domain.auth.dto.KakaoInfo;
//import shop.cazait.domain.auth.dto.KakaoToken;
//
//import java.net.URI;
//
//
//@FeignClient(name="kakaoClient",configuration = KakaoFeignConfiguration.class)
//public interface KakaoClient {
//    @GetMapping
//    KakaoCode kakaoLogin(URI baseUrl, @RequestParam("client_id") String restApiKey,
//                         @RequestParam("redirect_uri") String redirectUrl,
//                         @RequestParam("response_type") String responseType);
//    @PostMapping
//    KakaoInfo getInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);
//
//    @PostMapping
//    KakaoToken getToken(URI baseUrl, @RequestParam("client_id") String restApiKey,
//                        @RequestParam("redirect_uri") String redirectUrl,
//                        @RequestParam("code") String code,
//                        @RequestParam("grant_type") String grantType);
//}
