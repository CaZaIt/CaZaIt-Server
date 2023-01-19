package shop.cazait.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
public class PatchUserRes {
    private Long id;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public PatchUserRes(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static PatchUserRes of(User user) {
        return PatchUserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }
}
