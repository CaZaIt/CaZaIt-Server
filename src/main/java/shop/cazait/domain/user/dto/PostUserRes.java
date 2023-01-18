package shop.cazait.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUserRes {

    private Long id;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public PostUserRes(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static PostUserRes of(User user) {
        return PostUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }

}
