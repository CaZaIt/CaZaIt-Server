package shop.cazait.domain.auth.dto.sens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Schema(description = "문자 인증 (전송) 결과 Response :  문자 인증 (전송)시 Response 정보 ")
@Getter
@Builder
public class ExtSensSendMessageCodeOutDTO {
    @Schema(description = "요청 아이디")
    @NotBlank
    private String requestId;

    @Schema(description = "요청 시간", example = "2023-07-06T16:55:25.972")
    @NotBlank
    private LocalDateTime requestTime;
    @Schema(description = "요청 상태 코드(202 : 성공, 그 외: 실패)", example = "202")
    @NotBlank
    private String statusCode;
    @Schema(description = "요청 상태명", example = "success: 성공,  fail: 실패")
    @NotBlank
    private String statusName;

    @Builder
    ExtSensSendMessageCodeOutDTO(String requestId, LocalDateTime requestTime, String statusCode, String statusName){
        this.requestId = requestId;
        this.requestTime = requestTime;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }
}
