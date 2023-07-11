package shop.cazait.domain.auth.dto.sens;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "문자 인증번호 인증 결과 Response ")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AuthVerifyMessageCodeOutDTO {
    @Schema(description = "인증이 완료된 전화번호", example = "01012345678")
    private String recipientPhoneNumber;

    public static AuthVerifyMessageCodeOutDTO of(String recipientPhoneNumber){
        return AuthVerifyMessageCodeOutDTO.builder()
                .recipientPhoneNumber(recipientPhoneNumber)
                .build();
    }
}
