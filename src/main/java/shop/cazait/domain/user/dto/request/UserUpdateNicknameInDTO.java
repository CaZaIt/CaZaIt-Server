package shop.cazait.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 닉네임 수정 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateNicknameInDTO {


    @NotBlank(message="닉네임을 입력하세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{3,15}$",message = "올바른 닉네임 형식이 아닙니다")
    @Schema(description = "변경하려는 유저 닉네임",example = "카자잇")
    private String nickname;

}
