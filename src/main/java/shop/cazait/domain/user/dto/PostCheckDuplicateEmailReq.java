package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "이메일 중복확인 req: 이메일")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCheckDuplicateEmailReq {

    @Email(message = "이메일 형식을 지키세요.")
    @NotBlank
    @Schema(description = "이메일", example = "12345@gmail.com")
    private String email;

    @Builder
    public PostCheckDuplicateEmailReq(String email){
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .build();
    }
}
