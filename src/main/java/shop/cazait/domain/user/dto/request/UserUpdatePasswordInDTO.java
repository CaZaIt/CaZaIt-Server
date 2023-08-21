package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 비밀번호 수정시 비밀번호 입력 Request : 비밀번호 재설정")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePasswordInDTO {

    @Schema(description = "회원 id")
    private UUID id;

    @Schema(description = "변경할 유저의 새로운 비밀번호", example = "abc12345#$")
    private String password;
}
