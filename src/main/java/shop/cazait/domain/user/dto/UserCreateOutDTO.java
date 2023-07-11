package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Schema(description = "유저 정보 Response : 회원 가입된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserCreateOutDTO {

    @Schema(description = "회원 id", example = "1")
    private UUID id;

    @Schema(description = "아이디", example = "cazait1234")
    private String idNumber;

    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserCreateOutDTO of(User user) {
        return UserCreateOutDTO.builder()
                .id(user.getId())
                .idNumber(user.getIdNumber())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .build();
    }

}
