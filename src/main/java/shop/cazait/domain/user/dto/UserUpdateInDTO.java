package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "회원 수정 Request : 수정되어야 할 새로운 유저 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateInDTO {

    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "올바른 아이디 형식이 아닙니다")
    @NotBlank
    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountNumber;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @NotBlank
    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @NotBlank
    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @NotBlank(message="닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static User toEntity(UserUpdateInDTO userUpdateInDTO){
        return User.builder()
                .accountNumber(userUpdateInDTO.getAccountNumber())
                .password(userUpdateInDTO.getPhoneNumber())
                .phoneNumber(userUpdateInDTO.getPhoneNumber())
                .nickname(userUpdateInDTO.getNickname())
                .build();
    }
}
