package shop.cazait.domain.auth.api;

import static shop.cazait.global.error.status.SuccessStatus.ACCEPTED_SEND_MESSAGE;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.auth.Role;

import shop.cazait.domain.auth.dto.UserAuthenticateInDTO;
import shop.cazait.domain.auth.dto.UserAuthenticateOutDTO;
import shop.cazait.domain.auth.dto.kakao.ExtKakaoUserInfoOutDTO;
import shop.cazait.domain.auth.dto.sens.*;
import shop.cazait.domain.auth.service.AuthService;
import shop.cazait.domain.auth.service.KakaoService;
import shop.cazait.domain.master.exception.MasterException;
import shop.cazait.domain.user.dto.response.UserCreateOutDTO;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
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


    private final UserRepository userRepository;

    @Value("${rest-api-key}")
    private String clientId;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;

    @NoAuth
    @PostMapping("/log-in")
    @Operation(summary = "로그인", description = "유저/마스터 로그인 진행")
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
    @PostMapping("/send-authnumber")
    @Operation(summary = "문자 인증번호 발송", description = "인증 문자 받을 번호 입력하여, 인증 문자 발송")
    public SuccessResponse<AuthSendMessageCodeOutDTO> sendMessageCode(@RequestBody AuthSendAuthNumberCodeInDTO authSendAuthNumberCodeInDTO) throws NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, UnsupportedEncodingException, UserException {
        String recipientPhoneNumber = authSendAuthNumberCodeInDTO.getRecipientPhoneNumber();
        AuthSendMessageCodeOutDTO authSendMessageCodeOutDTO = authService.sendMessageCode(recipientPhoneNumber);
        return new SuccessResponse<>(ACCEPTED_SEND_MESSAGE, authSendMessageCodeOutDTO);
    }

    @NoAuth
    @PostMapping("/send-authnumber/test")
    @Operation(summary = "문자 인증번호 발송 테스트", description = "실제로 문자 발송은 진행하지 않음")
    public SuccessResponse<AuthSendMessageCodeTestOutDTO > sendMessageCodeTest(@RequestBody AuthSendAuthNumberCodeInDTO authSendAuthNumberCodeInDTO) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, UserException {
        String recipientPhoneNumber = authSendAuthNumberCodeInDTO.getRecipientPhoneNumber();
        AuthSendMessageCodeTestOutDTO authSendMessageCodeOutDTO = authService.sendMessageCodeTest(recipientPhoneNumber);
        return new SuccessResponse<>(ACCEPTED_SEND_MESSAGE, authSendMessageCodeOutDTO);
    }


    @NoAuth
    @PostMapping("/verify-authnumber")
    @Operation(summary = "문자 인증번호 인증", description = "문자로 받은 인증 번호를 입력하여, 적절한 인증번호인지 판단")
    public SuccessResponse<AuthVerifyMessageCodeOutDTO>verifyMessageCode(@RequestBody AuthVerifyMessageCodeInDTO authVerifyMessageCodeInDTO) throws UserException {
        String recipientPhoneNumber = authVerifyMessageCodeInDTO.getRecipientPhoneNumber();
        int verificationCode = authVerifyMessageCodeInDTO.getVerificationCode();
        AuthVerifyMessageCodeOutDTO authVerifyMessageCodeOutDTO = authService.verifyMessageCode(recipientPhoneNumber, verificationCode);
        return new SuccessResponse<>(SUCCESS, authVerifyMessageCodeOutDTO);
    }

    @NoAuth
    @GetMapping("/kakao/login")
    @Operation(summary = "카카오 로그인(웹)", description = "/kakao/callback로 redirect")
    public void kakaoLoginUser(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUrl);
    }


    @NoAuth
    @GetMapping(value = "/kakao/callback")
    @Operation(summary = "카카오 로그인(웹) redirecturl", description = "카카오 API에서 code를 쿼리 파라미터로 받아옴, 클라이언트에서 직접 호출 x")
    @Parameter(name = "code", description = "카카오 API에서 받아오는 code")
    public SuccessResponse<?> kakaoCallBack(@RequestParam String code) {
        log.info(code);

        ExtKakaoUserInfoOutDTO extKakaoUserInfoOutDTO = kakaoService.getInfo(code);
        long kakaoId = extKakaoUserInfoOutDTO.getId();
        //카카오 id로 조회, 있을 시 로그인 처리
        if(userRepository.findByKakaoId(kakaoId).isPresent()){
            UserAuthenticateOutDTO userAuthenticateOutDTO = kakaoService.kakaoLoginUser(kakaoId);
            return new SuccessResponse<>(SUCCESS,userAuthenticateOutDTO);
        }
        //카카오 id로 조회, 없을 시 회원가입 처리
        else{
            UserCreateOutDTO userCreateOutDTO = kakaoService.kakaoSignUpnUser(kakaoId);
            return new SuccessResponse<>(SUCCESS,userCreateOutDTO);
        }
    }
}



