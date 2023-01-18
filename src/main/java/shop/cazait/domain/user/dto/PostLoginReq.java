package shop.cazait.domain.user.dto;

import lombok.Builder;
import shop.cazait.domain.user.entity.User;

public class PostLoginReq {
    private Long id;
    private String email;
    private String password;

    @Builder
    public PostLoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
