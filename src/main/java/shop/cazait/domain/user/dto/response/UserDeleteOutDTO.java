package shop.cazait.domain.user.dto.response;

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

    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(name = "닉네임", example = "토마스")
    private String nickname;



    public static UserDeleteOutDTO of(User user){
        return UserDeleteOutDTO.builder()
                .id(user.getId())
                .accountName(user.getAccountName())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .build();
    }
}
