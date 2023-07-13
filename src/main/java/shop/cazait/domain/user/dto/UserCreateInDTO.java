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
public class UserCreateInDTO {

    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "올바른 아이디 형식이 아닙니다")
    @NotBlank
    @Schema(description = "아이디", example = "cazait1234")
    private String idNumber;

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

    @Builder
    public UserCreateInDTO(String idNumber, String password, String phoneNumber, String nickname){
        this.idNumber = idNumber;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public static User toEntity(UserCreateInDTO userCreateInDTO){
        return User.builder()
                .idNumber(userCreateInDTO.getIdNumber())
                .password(userCreateInDTO.getPassword())
                .phoneNumber(userCreateInDTO.getPhoneNumber())
                .nickname(userCreateInDTO.getNickname())
                .build();
    }

    public static UserCreateInDTO encryptUserPassword(UserCreateInDTO userCreateInDTO, String encryptedPassword){
        return UserCreateInDTO.builder()
                .idNumber(userCreateInDTO.getIdNumber())
                .password(encryptedPassword)
                .phoneNumber(userCreateInDTO.getPhoneNumber())
                .nickname(userCreateInDTO.getNickname())
                .build();
    }

}
