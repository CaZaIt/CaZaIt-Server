package shop.cazait.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "비밀번호 유저 정보 검증 Request : 아이디와 전화번호가 일치하는 경우")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserVerifyUserInfoInResetPasswordOutDTO {

    @NotBlank
    @Schema(description = "아이디", example = "cazait1234")
    private String accountName;

    @NotBlank
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;

    public static UserVerifyUserInfoInResetPasswordOutDTO of(User user){
        return UserVerifyUserInfoInResetPasswordOutDTO.builder()
                .accountName(user.getAccountName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
