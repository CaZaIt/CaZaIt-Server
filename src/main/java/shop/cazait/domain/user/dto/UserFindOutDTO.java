package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.user.entity.User;

import java.util.UUID;

@Schema(description = "유저 조회 Response : 조회된 회원의 유저 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserFindOutDTO {

    @Schema(description = "유저 식별 번호")
    private UUID id;

    @Schema(description = "아이디", example = "cazait1234")
    private String idNumber;

    @Schema(description = "Password", example = "abc12345#!")
    private String password;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    public static UserFindOutDTO of(User user){
        return UserFindOutDTO.builder()
                .id(user.getId())
                .idNumber(user.getIdNumber())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .build();
    }

}
