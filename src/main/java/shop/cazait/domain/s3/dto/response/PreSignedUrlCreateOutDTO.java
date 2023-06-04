package shop.cazait.domain.s3.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "이미지 업로드 Response", description = "미리 서명된 주소를 받아 이미지 업로드")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class PreSignedUrlCreateOutDTO {

    @Schema(description = "이미지 객체 Key", example = "cafe/caab93eb-e9ed-499e-be61-15cfa1069f73")
    String objectKey;
    @Schema(description = "미리 서명된 URL")
    String preSignedUrl;

    public static PreSignedUrlCreateOutDTO of(String objectKey, String preSignedUrl) {
        return PreSignedUrlCreateOutDTO.builder()
                .objectKey(objectKey)
                .preSignedUrl(preSignedUrl)
                .build();
    }


}
