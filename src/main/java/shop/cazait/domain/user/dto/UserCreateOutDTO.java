package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "유저 정보 Response : 회원 가입된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostUserRes {

    @Schema(description = "회원 id", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static PostUserRes of(User user) {
        return PostUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }

}
