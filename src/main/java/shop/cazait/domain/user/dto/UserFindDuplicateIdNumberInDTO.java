package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.user.entity.User;

import javax.validation.constraints.NotBlank;

@Schema(description = "이메일 중복확인 req: 이메일")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindDuplicateIdNumberInDTO {

    @NotBlank
    @Schema(description = "아이디", example = "cazait1234")
    private String idNumber;

    @Builder
    public UserFindDuplicateIdNumberInDTO(String idNumber){
        this.idNumber = idNumber;
    }

    public User toEntity(){
        return User.builder()
                .idNumber(idNumber)
                .build();
    }
}
