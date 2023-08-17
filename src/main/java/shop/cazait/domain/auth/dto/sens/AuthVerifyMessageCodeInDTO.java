package shop.cazait.domain.auth.dto.sens;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Schema(description = "문자 인증번호 인증 request")
public class AuthVerifyMessageCodeInDTO {

    @Schema(description = "인증 번호를 받은 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

    @Schema(description = "전송 받은 인증번호", example = "123456")
    private int verificationCode;


}
