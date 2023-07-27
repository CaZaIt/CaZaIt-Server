package shop.cazait.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "아이디 조회 Response")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserFindAccountNameOutDTO {
    @Schema(description = "회원 아이디")
    private String accountName;

    public static UserFindAccountNameOutDTO of (User user){
        return UserFindAccountNameOutDTO.builder()
                .accountName(user.getAccountNumber()).build();
    }
}
