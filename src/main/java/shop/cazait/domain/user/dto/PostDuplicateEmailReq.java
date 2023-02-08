package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "중복확인 Request : 이메일 중복확인에 필요한 유저 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDuplicateEmailReq {
    @Schema(description = "이메일", example = "12345@gmail.com")
    @Email
    @NotBlank
    private String email;

    @Builder
    public PostDuplicateEmailReq(String email) {
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .build();
    }
}
