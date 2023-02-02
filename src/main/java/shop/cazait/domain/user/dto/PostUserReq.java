package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;
import javax.validation.constraints.*;

@Schema(description = "유저 정보 Request : 회원 가입에 필요한 유저 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUserReq {

    @Email(message = "이메일 형식을 지키세요.")
    @NotBlank
    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @NotBlank
    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @NotBlank(message="닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public PostUserReq(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
