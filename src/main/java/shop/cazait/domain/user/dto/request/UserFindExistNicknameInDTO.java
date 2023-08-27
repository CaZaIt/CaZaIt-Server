package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;

@Schema(description = "닉네임 중복확인 req: 닉네임")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindExistNicknameInDTO {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{3,15}$",message = "올바른 닉네임 형식이 아닙니다")
    @Schema(description = "닉네임", example = "토마스")
    private String nickname;

    @NotBlank
    @Schema(description = "존재/존재하지 않는지 여부",example = "true/false")
    private String isExist;

    @Builder
    public UserFindExistNicknameInDTO(String nickname, String isExist) {
        this.nickname = nickname;
        this.isExist = isExist;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .build();
    }
}