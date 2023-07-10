package shop.cazait.domain.cafeimage.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafemenu.dto.request.MenuCreateInDTO;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Schema(description = "카페 이미지 저장 Request")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeImageCreateInDTO {

    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;

    @Schema(description = "카페 이미지 url")
    private List<String> imageUrl;

    public static CafeImage toEntity(Cafe cafe, String objectUrl) {
        return CafeImage.builder()
                .cafe(cafe)
                .imageUrl(objectUrl)
                .build();
    }

}
