package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

import java.util.UUID;

@Schema(description = "유저 삭제 Response : 삭제 완료된 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserDeleteOutDTO {

    @Schema(name = "회원 id")
    private UUID id;

    @Schema(name = "이메일", example = "12345@gmail.com")
    private String email;

    @Schema(name = "비밀번호", example = "abc12345#!")
    private String password;

    @Schema(name = "닉네임", example = "토마스")
    private String nickname;



    public static UserDeleteOutDTO of(User user){
        return UserDeleteOutDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }
}
