package shop.cazait.domain.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "이미지 업로드 Response : 미리 서명된 주소를 받아 이미지 업로드")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetPreSignedUrlRes {

    @Schema(description = "이미지 객체 Key", example = "cafe/caab93eb-e9ed-499e-be61-15cfa1069f73")
    String objectKey;
    @Schema(description = "미리 서명된 URL")
    String preSignedUrl;

    public static GetPreSignedUrlRes of(String objectKey, String preSignedUrl) {
        return GetPreSignedUrlRes.builder()
                .objectKey(objectKey)
                .preSignedUrl(preSignedUrl)
                .build();
    }


}
