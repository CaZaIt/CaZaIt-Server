package shop.cazait.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "유저 로그인 Request : 로그인시 필요한 유저 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserAuthenticateInDTO {

    @NotBlank(message="아이디를 입력하세요.")
    @Pattern(regexp = "^(?!\\d+$)[a-z\\d]{5,20}$", message = "올바른 아이디 형식이 아닙니다")
    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @NotBlank(message="비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;


    @Builder
    public UserAuthenticateInDTO(String accountName, String password) {
        this.accountName = accountName;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .accountName(accountName)
                .password(password)
                .build();
    }
}