package shop.cazait.domain.user.dto.request;

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
public class UserFindExistAccountNameInDTO {


    @NotBlank
    @Schema(description = "로그인 아이디", example = "cazait1234")
    private String accountName;

    @NotBlank
    @Schema(description = "존재/존재하지 않는지 여부",example = "true/false")
    private String isExist;

    @Builder
    public UserFindExistAccountNameInDTO(String accountName, String isExist){

        this.accountName = accountName;
        this.isExist = isExist; 
    }

    public User toEntity(){
        return User.builder()
                .accountName(accountName)
                .build();
    }
}
