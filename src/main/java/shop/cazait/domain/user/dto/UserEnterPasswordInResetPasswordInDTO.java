package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 비밀번호 수정 Request : 비밀번호 재설정")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEnterPasswordInResetPasswordInDTO {

    @Schema(description = "비밀번호 재설정 문자 발송 및 인증이 완료된 전화번호", example = "01012345678")
    private String userPhoneNumber;

    @Schema(description = "변경할 유저 비밀번호", example = "abc12345#$")
    private String password;
}
