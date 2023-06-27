package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "유저 수정 Response : 수정 완료된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserUpdateOutDTO {

    @Schema(description = "회원 id", example = "1")
    private Long id;

    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserUpdateOutDTO of(User user) {
        return UserUpdateOutDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }

}
