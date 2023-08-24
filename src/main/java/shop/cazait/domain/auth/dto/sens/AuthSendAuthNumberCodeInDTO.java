package shop.cazait.domain.auth.dto.sens;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "문자 인증번호 발송 request : 문자 인증번호를 받을 정보 입력")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthSendAuthNumberCodeInDTO {

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "인증 번호가 발송될 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

    @Builder
    AuthSendAuthNumberCodeInDTO(String recipientPhoneNumber){
        this.recipientPhoneNumber = recipientPhoneNumber;
    }
}