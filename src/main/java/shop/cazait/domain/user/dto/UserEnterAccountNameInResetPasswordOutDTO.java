package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "유저 비밀번호 수정 Response : 비밀번호 변경 시 입력한 아이디")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserEnterAccountNameInResetPasswordOutDTO {
    @Schema(description = "회원 아이디")
    private String accountName;

    public static UserEnterAccountNameInResetPasswordOutDTO of (User user){
        return UserEnterAccountNameInResetPasswordOutDTO.builder()
                .accountName(user.getAccountNumber()).build();
    }
}
