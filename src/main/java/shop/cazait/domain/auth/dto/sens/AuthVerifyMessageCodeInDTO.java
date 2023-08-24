package shop.cazait.domain.auth.dto.sens;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Schema(description = "문자 인증번호 인증 request")
public class AuthVerifyMessageCodeInDTO {

    @NotBlank
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "인증 번호를 받은 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

    @Schema(description = "전송 받은 인증번호", example = "123456")
    private int verificationCode;


}
