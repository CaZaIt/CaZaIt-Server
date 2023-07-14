package shop.cazait.domain.auth.dto.sens;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Schema(description = "문자 인증 (전송) Sens Request :  문자 인증 (전송) Sens API 호출 시  Request의 필요한 정보 ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExtSensSendMessageCodeInDTO {

    @Schema(description = "SMS Type", example = "SMS, LMS, MMS")
    @NotBlank
    private String type;

    @Schema(description = "메시지 Type(default: COMM)", example = "COMM: 일반메시지, AD: 광고메시지")
    private String contentType;

    @Schema(description = "국가 번호(default: 82)", example = "82")
    private String countryCode;

    @NotBlank
    @Schema(description = "발신번호(사전 등록된 발신번호만 사용 가능)", example = "01012345678")
    private String from;

    @NotBlank
    @Schema(description = "기본 메시지 정보 , messages를 정의하지 않으면 지정된 값으로 발송", example = "[카자잇]")
    private String content;

    @NotBlank
    @Schema(description = "개별 메시지 정보")
    private List<AuthSendMessageInfoInDTO> messages;

    @Builder
    public ExtSensSendMessageCodeInDTO(String type, String contentType, String countryCode, String from, String content, List<AuthSendMessageInfoInDTO> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }
}
