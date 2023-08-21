package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "계정정보관리 페이지에서의 비밀번호 검증 Request : 현재 로그인한 유저의 비밀번호가 적절한지")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyPasswordInDTO {

    @Schema(description = "로그인한 회원의 id")
    private UUID id;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @NotBlank
    @Schema(description = "회원이 입력한 비밀번호", example = "abc12345#!")
    private String password;
}
