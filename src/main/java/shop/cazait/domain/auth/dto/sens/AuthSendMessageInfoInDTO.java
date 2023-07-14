package shop.cazait.domain.auth.dto.sens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "문자 메시지 전송 내용 : ExtSensSendMessageCodeInDTO에 포함됨 ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthSendMessageInfoInDTO {

    @Schema(description = "문자가 발송될 전화번호", example = "01012345678")
    private String to;

    @Schema(description = "문자 메시지 내용", example = "[카자잇] 인증번호[123456]를 입력해주세요")
    private String content;

    @Builder
    public AuthSendMessageInfoInDTO(String to, String content){
        this.to = to;
        this.content = content;
    }
}
