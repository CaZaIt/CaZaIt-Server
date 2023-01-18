package shop.cazait.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
public class PostLoginRes {
    private Long id;

    private String email;

    @Builder
    public PostLoginRes(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static PostLoginRes of(User user){
        return PostLoginRes
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
