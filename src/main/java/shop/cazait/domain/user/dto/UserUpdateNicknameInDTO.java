package shop.cazait.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 닉네임 수정 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateNicknameInDTO {


    @Schema(description = "회원 id")
    private UUID id;

    @Schema(description = "변경하려는 유저 닉네임")
    private String nickname;

}
