package shop.cazait.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

@Schema(description = "전화번호 중복확인 req")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindExistPhoneNumberInDTO {
    @NotBlank
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "존재/존재하지 않는지 여부",example = "true/false")
    private String isExist;

    @Builder
    public UserFindExistPhoneNumberInDTO(String phoneNumber, String isExist){

        this.phoneNumber = phoneNumber;
        this.isExist = isExist;
    }

    public User toEntity(){
        return User.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

}
