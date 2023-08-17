package shop.cazait.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.cazait.domain.auth.Role;
import shop.cazait.domain.auth.client.SensClient;
import shop.cazait.domain.auth.dto.*;
import shop.cazait.domain.auth.dto.sens.*;
import shop.cazait.domain.master.exception.MasterException;
import shop.cazait.domain.master.service.MasterService;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;
import shop.cazait.domain.user.service.UserService;
import shop.cazait.global.error.status.ErrorStatus;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static shop.cazait.domain.auth.Role.USER;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserService userService;

    private final MasterService masterService;

    private final UserRepository userRepository;

    private final SensClient sensClient;

    private final HttpSession httpSession;

    @Value("${credentials.api-key.access-key}")
    private String accessKey;
    @Value("${credentials.api-key.secret-key}")
    private String secretKey;
    @Value("${credentials.service-id.SMS}")
    private String serviceId;

    @Value("${user-info.sender-phone-number}")
    private String senderPhoneNumber;

    private static final int smsVerifyTime = 180;
    private static final Random random = new SecureRandom();

    public UserAuthenticateOutDTO reIssueTokensByRole(Role exactRole, String accessToken, String refreshToken) throws MasterException, UserException {
        if (exactRole.equals(USER)) {
            return userService.reIssueTokens(accessToken, refreshToken);
        } else {
            return masterService.issueAccessToken(accessToken, refreshToken);
        }
    }

    public UserAuthenticateOutDTO logInByRole(Role exactRole, UserAuthenticateInDTO userAuthenticateInDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MasterException {
        if (exactRole.equals(USER)) {
            return userService.logInUser(userAuthenticateInDTO);
        } else {
            return masterService.LoginMaster(userAuthenticateInDTO);
        }
    }

    public AuthSendMessageCodeOutDTO sendMessageCode(String recipientPhoneNumber) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, UserException {

//        if(userRepository.findByPhoneNumber(recipientPhoneNumber).isPresent()){
//            throw new UserException(EXIST_PHONENUMBER);
//        }

        /**Body Content**/
        //인증번호 및 메시지 발송 내용 생성
        int verificationCode = random.nextInt(900000) + 100000;
        String customMessageContent = "[카자잇] 인증번호["+verificationCode+"]를 입력해주세요";
        System.out.println("verificationCode = " + verificationCode);
        List<AuthSendMessageInfoInDTO> messages = new ArrayList<>();

        AuthSendMessageInfoInDTO authSendMessageInfoInDTO = AuthSendMessageInfoInDTO.builder().
                            to(recipientPhoneNumber).
                            content(customMessageContent).build();

        messages.add(authSendMessageInfoInDTO);

        /**Headers Content**/
        //현재 시간
        long currentTime = System.currentTimeMillis();
        //시그니처 생성
        String signature = createSignature(currentTime);

        /**SENS API 통신 **/
        //Request DTO 생성
        ExtSensSendMessageCodeInDTO extSensSendMessageCodeInDTO = ExtSensSendMessageCodeInDTO.builder().
                type("SMS").
                contentType("COMM").
                countryCode("82").
                from(senderPhoneNumber).
                content("[카자잇]").
                messages(messages).build();

        //요청
        ExtSensSendMessageCodeOutDTO extSensSendMessageCodeOutDTO = sensClient.sendMessage(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"),
                String.valueOf(currentTime),
                this.accessKey,
                signature,
                extSensSendMessageCodeInDTO);


        LocalDateTime currentDateTime = LocalDateTime.now();

//        ExtSensSendMessageCodeOutDTO extSensSendMessageCodeOutDTO = ExtSensSendMessageCodeOutDTO.builder().
//                requestId("1").
//                requestTime(currentDateTime).
//                statusCode("202").
//                statusName("success").build();

        /**전송 성공시 세션에 (전화번호, 인증번호) 저장 **/
        Optional<Object> attribute = Optional.ofNullable(httpSession.getAttribute(recipientPhoneNumber));
        httpSession.setMaxInactiveInterval(smsVerifyTime);
        httpSession.setAttribute(recipientPhoneNumber,verificationCode);
        
        return AuthSendMessageCodeOutDTO.of(recipientPhoneNumber,extSensSendMessageCodeOutDTO);
    }

    public String createSignature(long currentTime) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {

        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = String.valueOf(currentTime);
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }


    public AuthVerifyMessageCodeOutDTO verifyMessageCode(String recipientPhoneNumber , int verificationCode) throws UserException {

        Optional<Object> attribute = Optional.ofNullable(httpSession.getAttribute(recipientPhoneNumber));
        //세션에서 key가 휴대전화번호인 값이 없을 시 예외 발생 (만료된 인증번호)
        attribute.orElseThrow(()->new UserException(ErrorStatus.EXPIRED_VERIFICATION_CODE));

        int authNumberInSession = (int)attribute.get();

        //인증 성공
        if(verificationCode==(authNumberInSession)){
            return AuthVerifyMessageCodeOutDTO.of(recipientPhoneNumber);
             }
        else{//인증 실패 (잘못된 인증 번호)
            throw new UserException(ErrorStatus.INVALID_VERIFICATION_CODE);
        }
    }
    public AuthSendMessageCodeTestOutDTO sendMessageCodeTest(String recipientPhoneNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, UserException {

//        if(userRepository.findByPhoneNumber(recipientPhoneNumber).isPresent()){
//            throw new UserException(EXIST_PHONENUMBER);
//        }

        /**Body Content**/
        //인증번호 및 메시지 발송 내용 생성
        int verificationCode = random.nextInt(900000) + 100000;
        String customMessageContent = "[카자잇] 인증번호["+verificationCode+"]를 입력해주세요";
        System.out.println("verificationCode = " + verificationCode);
        List<AuthSendMessageInfoInDTO> messages = new ArrayList<>();

        AuthSendMessageInfoInDTO authSendMessageInfoInDTO = AuthSendMessageInfoInDTO.builder().
                to(recipientPhoneNumber).
                content(customMessageContent).build();

        messages.add(authSendMessageInfoInDTO);

        /**Headers Content**/
        //현재 시간
        long currentTime = System.currentTimeMillis();
        //시그니처 생성
        String signature = createSignature(currentTime);

        /**SENS API 통신 **/
        //Request DTO 생성
        ExtSensSendMessageCodeInDTO extSensSendMessageCodeInDTO = ExtSensSendMessageCodeInDTO.builder().
                type("SMS").
                contentType("COMM").
                countryCode("82").
                from(senderPhoneNumber).
                content("[카자잇]").
                messages(messages).build();

        //요청
//        ExtSensSendMessageCodeOutDTO extSensSendMessageCodeOutDTO = sensClient.sendMessage(
//                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"),
//                currentTime.toString(),
//                this.accessKey,
//                signature,
//                extSensSendMessageCodeInDTO);


        LocalDateTime currentDateTime = LocalDateTime.now();

        ExtSensSendMessageCodeOutDTO extSensSendMessageCodeOutDTO = ExtSensSendMessageCodeOutDTO.builder().
                requestId("1").
                requestTime(currentDateTime).
                statusCode("202").
                statusName("success").build();

        /**전송 성공시 세션에 (전화번호, 인증번호) 저장 **/
        httpSession.setMaxInactiveInterval(smsVerifyTime);
        httpSession.setAttribute(recipientPhoneNumber,verificationCode);

        return AuthSendMessageCodeTestOutDTO .of(recipientPhoneNumber, verificationCode, extSensSendMessageCodeOutDTO);
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

