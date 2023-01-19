package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;
@Getter
@ApiModel(value = "PostLoginRes/로그인 정보",description = "회원 로그인 완료된 회원 정보 dto")
public class PostLoginRes {
    @ApiModelProperty(value = "회원 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
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
