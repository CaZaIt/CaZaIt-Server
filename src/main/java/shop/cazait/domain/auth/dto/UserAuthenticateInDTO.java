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

    @Pattern(regexp = "^[a-z0-9]{5,20}$")
    @NotBlank
    @Schema(description = "아이디", example = "cazait1234")
    private String idNumber;
    @Schema(description = "비밀번호", example = "abc12345#!")
    @NotBlank
    private String password;


    @Builder
    public UserAuthenticateInDTO(String idNumber, String password) {
        this.idNumber = idNumber;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .idNumber(idNumber)
                .password(password)
                .build();
    }
}