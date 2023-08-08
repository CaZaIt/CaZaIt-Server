package shop.cazait.domain.cafeimage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.cafeimage.entity.CafeImage;

@Schema(description = "카페 이미지 저장 Request")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeImageCreateInDTO {

    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @Schema(description = "카페 이미지 url")
    private List<String> imageUrl;

    public static CafeImage toEntity(Cafe cafe, String objectUrl) {
        return CafeImage.builder()
                .cafe(cafe)
                .imageUrl(objectUrl)
                .build();
    }

}
