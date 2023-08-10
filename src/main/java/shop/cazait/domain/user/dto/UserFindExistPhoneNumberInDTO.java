package shop.cazait.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
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
