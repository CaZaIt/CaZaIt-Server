package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "아이디 중복확인 req: 아이디")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindDuplicateAccountNumberInDTO {

    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "올바른 아이디 형식이 아닙니다")
    @NotBlank
    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountNumber;

    @Builder
    public UserFindDuplicateAccountNumberInDTO(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public User toEntity(){
        return User.builder()
                .accountNumber(accountNumber)
                .build();
    }
}
