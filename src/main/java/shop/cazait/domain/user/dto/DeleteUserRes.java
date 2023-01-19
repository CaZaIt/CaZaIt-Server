package shop.cazait.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
public class DeleteUserRes {

    private Long id;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public DeleteUserRes(Long id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public static DeleteUserRes of(User user){
        return DeleteUserRes
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }
}
