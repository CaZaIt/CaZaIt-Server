package shop.cazait.domain.auth.api;

import static shop.cazait.global.error.status.SuccessStatus.ACCEPTED_SEND_MESSAGE;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.auth.Role;


import shop.cazait.domain.auth.dto.UserAuthenticateInDTO;
import shop.cazait.domain.auth.dto.UserAuthenticateOutDTO;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserInfoOutDTO;
import shop.cazait.domain.auth.dto.sens.AuthSendMessageCodeInDTO;
import shop.cazait.domain.auth.dto.sens.AuthSendMessageCodeOutDTO;
import shop.cazait.domain.auth.dto.sens.AuthVerifyMessageCodeInDTO;
import shop.cazait.domain.auth.dto.sens.AuthVerifyMessageCodeOutDTO;
import shop.cazait.domain.auth.service.AuthService;
import shop.cazait.domain.auth.service.KakaoService;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.user.dto.UserCreateOutDTO;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.domain.user.service.UserService;
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

    private final KakaoService kakaoService;

    private final UserService userService;

    private final UserRepository userRepository;

    @NoAuth
    @PostMapping("/log-in")
    @Operation(summary = "회원 로그인", description = "이메일과 로그인을 통해 로그인을 진행")
    @Parameter(name = "role", description = "유저인지 마스터인지(user/master)", example = "master")
    public SuccessResponse<UserAuthenticateOutDTO> logIn(
            @RequestParam @NotBlank String role,
            @RequestBody @Valid UserAuthenticateInDTO userAuthenticateInDTO)
            throws UserException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {

        Role exactRole = Role.of(role);
        UserAuthenticateOutDTO userAuthenticateOutDTO = authService.logInByRole(exactRole, userAuthenticateInDTO);
        return new SuccessResponse<>(SUCCESS, userAuthenticateOutDTO);
    }

    @NoAuth
    @GetMapping(value = "/refresh")
    @Operation(summary = "토큰 재발급", description = "인터셉터에서 accesstoken이 만료되고 난 후 클라이언트에서 해당 api로 토큰 재발급 요청 필요")
    @Parameters({
            @Parameter(name = "role", description = "유저인지 마스터인지(user/master)", example = "user"),
            @Parameter(name = "Refresh-Token", description = "발급 받은 refreshtoken"),
    })
    public SuccessResponse<UserAuthenticateOutDTO> refreshToken(
            @RequestParam @NotBlank String role,
            @RequestHeader(value = "Refresh-Token") String refreshToken) throws UserException, BaseException, MasterException {

        String accessToken = jwtService.getJwtFromHeader();
        Role exactRole = Role.of(role);
        UserAuthenticateOutDTO userAuthenticateOutDTO = authService.reIssueTokensByRole(exactRole, accessToken, refreshToken);
        return new SuccessResponse<>(SUCCESS, userAuthenticateOutDTO);
    }

    @NoAuth
    @PostMapping("/messages/codes/send")
    @Operation(summary = "문자 인증번호 발송", description = "인증 문자 받을 번호 입력하여, 인증 문자 발송")
    public SuccessResponse<AuthSendMessageCodeOutDTO> sendMessageCode(@RequestBody AuthSendMessageCodeInDTO userSensAuthenticateInDTO) throws NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException, UnsupportedEncodingException, JsonProcessingException, UserException {
        String recipientPhoneNumber = userSensAuthenticateInDTO.getRecipientPhoneNumber();
        AuthSendMessageCodeOutDTO authSendMessageCodeOutDTO = authService.sendMessageCode(recipientPhoneNumber);
        return new SuccessResponse<>(ACCEPTED_SEND_MESSAGE, authSendMessageCodeOutDTO);
    }

    @NoAuth
    @PostMapping("/messages/codes/send/test")
    @Operation(summary = "문자 인증번호 발송 테스트", description = "실제로 문자 발송은 진행하지 않음")
    public SuccessResponse<AuthSendMessageCodeOutDTO> sendMessageCodeTest(@RequestBody AuthSendMessageCodeInDTO userSensAuthenticateInDTO) throws NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException, UnsupportedEncodingException, JsonProcessingException, UserException {
        String recipientPhoneNumber = userSensAuthenticateInDTO.getRecipientPhoneNumber();
        AuthSendMessageCodeOutDTO authSendMessageCodeOutDTO = authService.sendMessageCodeTest(recipientPhoneNumber);
        return new SuccessResponse<>(ACCEPTED_SEND_MESSAGE, authSendMessageCodeOutDTO);
    }

    @NoAuth
    @PostMapping("/messages/codes/verify")
    @Operation(summary = "문자 인증번호 인증", description = "문자로 받은 인증 번호를 입력하여, 적절한 인증번호인지 판단")
    public SuccessResponse<AuthVerifyMessageCodeOutDTO>verifyMessageCode(@RequestBody AuthVerifyMessageCodeInDTO authVerifyMessageCodeInDTO) throws UserException {
        String recipientPhoneNumber = authVerifyMessageCodeInDTO.getRecipientPhoneNumber();
        Integer verificationCode = authVerifyMessageCodeInDTO.getVerificationCode();
        AuthVerifyMessageCodeOutDTO authVerifyMessageCodeOutDTO = authService.verifyMessageCode(recipientPhoneNumber, verificationCode);
        return new SuccessResponse<>(SUCCESS, authVerifyMessageCodeOutDTO);
    }

    @NoAuth
    @GetMapping("/kakao/login")
    @Operation(summary = "카카오 로그인(웹)", description = "/kakao/callback로 redirect")
    public void kakaoLoginUser(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=59998cc010f4d694d452c4c1b86e6475&redirect_uri=http://localhost:8080/api/auths/kakao/callback");
    }


    @NoAuth
    @GetMapping(value = "/kakao/callback")
    public SuccessResponse<?> kakaoCallBack(@RequestParam String code) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {
        log.info(code);

        ExtKakaoUserInfoOutDTO extKakaoUserInfoOutDTO = kakaoService.getInfo(code);
        Long kakaoId = extKakaoUserInfoOutDTO.getId();
        if(userRepository.findByKakaoId(kakaoId).isPresent()){
            UserAuthenticateOutDTO userAuthenticateOutDTO = kakaoService.kakaoLoginUser(kakaoId);
            return new SuccessResponse<>(SUCCESS,userAuthenticateOutDTO);
        }
        else{
            UserCreateOutDTO userCreateOutDTO = kakaoService.kakaoSignUpnUser(kakaoId);
            return new SuccessResponse<>(SUCCESS,userCreateOutDTO);
        }
    }
}



