package shop.cazait.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "아이디 찾기 request, 문자 발송 및 인증이 완료된 후, 인증한 휴대전화번호를 전송")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserFindAccountNameInDTO {

    @Schema(description = "아이디 찾기 문자 발송 및 인증이 완료된 전화번호", example = "01012345678")
    private String userPhoneNumber;

    @Builder
    UserFindAccountNameInDTO(String userPhoneNumber){
        this.userPhoneNumber = userPhoneNumber;
    }
}
