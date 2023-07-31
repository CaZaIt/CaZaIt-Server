package shop.cazait.domain.cafeimage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafeimage.entity.CafeImage;

@Schema(description = "카페 이미지 조회 Response : 카페 이미지 정보")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeImageGetOutDTO {

    @Schema(description = "카페 이미지 url", example = "https://cazait-bucket.s3.ap-northeast-2.amazonaws.com/cafe-image/abcdefghijklmnopqrstuvwxyz")
    private List<String> imageUrl;

    public static CafeImageGetOutDTO of(List<CafeImage> cafeImages) {
        return CafeImageGetOutDTO.builder()
                .imageUrl(cafeImages.stream()
                        .map(cafeImage -> cafeImage.getImageUrl())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
