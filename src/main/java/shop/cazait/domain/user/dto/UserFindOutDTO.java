package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

import java.util.UUID;

@Schema(description = "유저 조회 Response : 조회된 회원의 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserFindOutDTO {

    @Schema(description = "유저 식별 번호")
    private UUID id;

    @Schema(description = "Email", example = "12345@gmail.com")
    private String email;

    @Schema(description = "Password", example = "abc12345#!")
    private String password;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserFindOutDTO of(User user){
        return UserFindOutDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }

}
