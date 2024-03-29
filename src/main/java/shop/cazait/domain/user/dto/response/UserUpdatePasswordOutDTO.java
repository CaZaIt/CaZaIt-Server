package shop.cazait.domain.user.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

import java.util.UUID;

@Schema(description = "유저 비밀번호 수정시 비밀번호 입력 Response : 비밀번호 수정된 유저의 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserUpdatePasswordOutDTO  {
    @Schema(description = "회원 id")
    private UUID id;

    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @Schema(description = "비밀번호", example = "abc12345#!")
    private String password;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserUpdatePasswordOutDTO  of (User user, String password){
        return UserUpdatePasswordOutDTO .builder()
                .id(user.getId())
                .accountName(user.getAccountName())
                .password(password)
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .build();
    }

}
