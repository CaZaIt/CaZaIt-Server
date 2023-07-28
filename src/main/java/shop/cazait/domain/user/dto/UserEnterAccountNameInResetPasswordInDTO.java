package shop.cazait.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 비밀번호 수정시 아이디 입력 Request : 비밀번호 변경 시 가입한 아이디 입력")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEnterAccountNameInResetPasswordInDTO {
    @Schema(description = "비밀번호 찾기 시 입력된 아이디", example = "cazait1234")
    private String accountName;
}
