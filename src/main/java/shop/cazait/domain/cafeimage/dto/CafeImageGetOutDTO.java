package shop.cazait.domain.cafeimage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafeimage.entity.CafeImage;

@Schema(description = "카페 이미지 조회 Response : 카페 이미지 정보")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeImageGetOutDTO {

    @JsonProperty
    @Schema(description = "카페 이미지 url", example = "https://cazait-bucket.s3.ap-northeast-2.amazonaws.com/cafe-image/abcdefghijklmnopqrstuvwxyz")
    private String imageUrl;

    public static CafeImageGetOutDTO of(CafeImage cafeImage) {
        return CafeImageGetOutDTO.builder()
                .imageUrl(cafeImage.getImageUrl())
                .build();
    }
}
