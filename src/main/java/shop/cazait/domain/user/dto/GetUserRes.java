package shop.cazait.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Getter
public class GetUserRes {

    private Long id;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public GetUserRes(Long id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public static GetUserRes of(User user){
        return GetUserRes
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }
}
