package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "비밀번호 유저 정보 검증 Request : 아이디와 전화번호가 일치하는지 검증")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyUserInfoInResetPasswordInDTO {

    @NotBlank
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
}
