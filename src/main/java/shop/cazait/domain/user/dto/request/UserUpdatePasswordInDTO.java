package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 비밀번호 수정 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePasswordInDTO {

    @NotBlank(message="비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @Schema(description = "변경할 유저의 새로운 비밀번호", example = "abc12345#$")
    private String password;
}
