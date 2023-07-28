package shop.cazait.domain.auth.dto.sens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "문자 인증번호 발송 request (비밀번호 찾기): 유저 정보가 다를 시 문자 전송 안됨")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthSendMessageCodeInResetPasswordInDTO {
    @Schema(description = "비밀번호 찾기 시 입력된 아이디", example = "cazait1234")
    private String accountName;

    @Schema(description = "문자 발송 및 인증을 받을 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

}
