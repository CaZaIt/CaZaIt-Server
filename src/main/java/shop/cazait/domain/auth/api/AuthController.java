package shop.cazait.domain.auth.api;

import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.auth.Role;

import shop.cazait.domain.auth.dto.PostLoginReq;
import shop.cazait.domain.auth.dto.PostLoginRes;
import shop.cazait.domain.auth.service.AuthService;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.JwtService;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.exception.BaseException;

@Slf4j
@Tag(name = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
public class AuthController {


    private final JwtService jwtService;

    private final AuthService authService;

    @NoAuth
    @PostMapping("/log-in")
    @Operation(summary = "회원 로그인", description = "이메일과 로그인을 통해 로그인을 진행")
    @Parameter(name = "role", description = "유저인지 마스터인지(user/master)")
    public SuccessResponse<PostLoginRes> logIn(
            @RequestParam @NotBlank String role,
            @RequestBody @Valid PostLoginReq postLoginReq)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {

        Role exactRole = Role.of(role);
        PostLoginRes postLoginRes = authService.logInByRole(exactRole, postLoginReq);
        return new SuccessResponse<>(SUCCESS, postLoginRes);
    }


    @NoAuth
    @GetMapping(value = "/refresh/{userIdx}")
    @Operation(summary = "토큰 재발급", description = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
    @Parameter(name = "role", description = "유저인지 마스터인지(user/master)")
    public SuccessResponse<PostLoginRes> refreshToken(
            @PathVariable(name = "userIdx") Long userIdx,
            @RequestParam @NotBlank String role,
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestHeader(value = "REFRESH-TOKEN") String refreshToken) throws UserException, BaseException, MasterException {

        jwtService.isValidAccessTokenId(userIdx);
        Role exactRole = Role.of(role);
        PostLoginRes postLoginRes = authService.reIssueTokensByRole(exactRole, accessToken, refreshToken, userIdx);
        return new SuccessResponse<>(SUCCESS, postLoginRes);
    }
}

//    @NoAuth
//    @GetMapping(value = "/kakao")
//    public void kakaoCallBack(@RequestParam String code) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//
//        KakaoAccount kakaoAccountInfo = authService.getInfo(code).getKakaoAccount();
//        System.out.println("kakaoAccountInfo = " + kakaoAccountInfo);
//        System.out.println("kakaoAccountInfo.getEmail() = " + kakaoAccountInfo.getEmail());
//        System.out.println("kakaoAccountInfo.getProfile() = " + kakaoAccountInfo.getProfile());
//
//        authService.loginByKakao(kakaoAccountInfo.getEmail());
//    }

