package shop.cazait.domain.auth.dto.sens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "문자 인증번호 발송 response :  문자 인증번호 발송 결과 (테스트) ")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AuthSendMessageCodeOutDTOTest {
    @Schema(description = "인증 번호가 발송된 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

    @Schema(description = "요청 시간", example = "2023-07-06T16:55:25.972")
    @NotBlank
    private LocalDateTime requestTime;

    @Schema(description = "인증 번호", example = "123456")
    @NotBlank
    private Integer verificationCode;

    public static AuthSendMessageCodeOutDTOTest of(String recipientPhoneNumber, Integer verificationCode, ExtSensSendMessageCodeOutDTO extSensSendMessageCodeOutDTO) {
        return AuthSendMessageCodeOutDTOTest.builder().
                recipientPhoneNumber(recipientPhoneNumber).
                requestTime(extSensSendMessageCodeOutDTO.getRequestTime()).
                verificationCode(verificationCode).build();
    }
}
