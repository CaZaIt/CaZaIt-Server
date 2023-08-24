package shop.cazait.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "아이디 찾기 request : 문자 발송 및 인증이 완료된 후, 인증한 휴대전화번호를 전송")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserFindAccountNameInDTO {

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다")
    @Schema(description = "아이디 찾기 문자 발송 및 인증이 완료된 전화번호", example = "01012345678")
    private String userPhoneNumber;

    @Builder
    UserFindAccountNameInDTO(String userPhoneNumber){
        this.userPhoneNumber = userPhoneNumber;
    }
}
