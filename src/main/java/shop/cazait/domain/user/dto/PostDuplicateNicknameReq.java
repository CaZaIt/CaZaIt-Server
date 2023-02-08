package shop.cazait.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;

@Schema(description = "중복확인 Request : 닉네임 중복확인에 필요한 유저 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDuplicateNicknameReq {
    @NotBlank(message="닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    @Builder
    public PostDuplicateNicknameReq(String nickname) {
        this.nickname = nickname;
    }

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .build();
    }
}
