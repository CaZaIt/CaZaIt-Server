package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "비밀번호 유저 정보 검증 Request : 아이디와 전화번호가 일치하는지 검증")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVerifyUserInfoInResetPasswordInDTO {

    @NotBlank
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
}
