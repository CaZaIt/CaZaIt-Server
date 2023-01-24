package shop.cazait.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "PostLoginReq/로그인 정보",description = "회원 로그인시 필요한 회원 정보 dto")
public class PostLoginReq {
    @ApiModelProperty(value = "회원 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "이메일", example = "12345@gmail.com")
    private String email;
    @ApiModelProperty(value = "비밀번호", example = "12345#!@#")
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
