package shop.cazait.domain.user.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

@Schema(description = "유저 닉네임 수정 Response")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserUpdateNicknameOutDTO {

    @Schema(description = "회원 id")
    private UUID id;

    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserUpdateNicknameOutDTO of (User user){
        return UserUpdateNicknameOutDTO.builder()
                .id(user.getId())
                .accountName(user.getAccountName())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .build();
    }

}
