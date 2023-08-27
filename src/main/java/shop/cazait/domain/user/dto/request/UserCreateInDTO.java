package shop.cazait.domain.user.dto.request;

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
public class UserCreateInDTO {

    @NotBlank(message="아이디를 입력하세요.")
    @Pattern(regexp = "^(?!\\d+$)[a-z\\d]{5,20}$", message = "올바른 아이디 형식이 아닙니다")
    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @NotBlank(message="비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함하여 사용하세요.")
    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @NotBlank(message="닉네임을 입력하세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{3,15}$",message = "올바른 닉네임 형식이 아닙니다")
    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public UserCreateInDTO(String accountName, String password, String phoneNumber, String nickname){
        this.accountName = accountName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public static User toEntity(UserCreateInDTO userCreateInDTO){
        return User.builder()
                .accountName(userCreateInDTO.getAccountName())
                .password(userCreateInDTO.getPassword())
                .phoneNumber(userCreateInDTO.getPhoneNumber())
                .nickname(userCreateInDTO.getNickname())
                .build();
    }

    public UserCreateInDTO encryptUserUpdateDTO(UserCreateInDTO userCreateInDTO, String encryptedPassword){
        this.password = encryptedPassword;

        return this;
    }

}
