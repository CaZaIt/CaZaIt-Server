package shop.cazait.domain.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.menu.entity.Menu;

@Schema(name = "메뉴 등록 Request", description = "등록할 메뉴에 대한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCreateInDTO {

    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @Schema(description = "이름", example = "아이스 아메리카노", required = true)
    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "가격", example = "4500", required = true)
    @NotBlank(message = "메뉴 가격을 입력해주세요.")
    private Integer price;

    @Schema(description = "이미지 URL", example = "https://cazait-bucket.s3.ap-northeast-2.amazonaws.com/cafe-image/abcdefghijklmnopqrstuvwxyz", required = true)
    private String ImageUrl;

    public static Menu toEntity(Cafe cafe, MenuCreateInDTO menuCreateInDTO) {
        return Menu.builder()
                .cafe(cafe)
                .name(menuCreateInDTO.getName())
                .description(menuCreateInDTO.getDescription())
                .price(menuCreateInDTO.getPrice())
                .imageUrl(menuCreateInDTO.getImageUrl())
                .build();
    }

}
