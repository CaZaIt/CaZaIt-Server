package shop.cazait.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.auth.Role;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Schema(description = "유저 로그인 Request : 로그인시 필요한 유저 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostLoginReq {

    @Schema(description = "이메일", example = "12345@gmail.com")
    @NotBlank
    private String email;
    @Schema(description = "비밀번호", example = "abc12345#!")
    @NotBlank
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