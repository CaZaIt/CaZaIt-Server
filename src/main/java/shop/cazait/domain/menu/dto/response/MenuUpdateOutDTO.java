package shop.cazait.domain.menu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.menu.entity.Menu;

@Schema(name = "메뉴 수정 Response ", description = "수정한 메뉴에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuUpdateOutDTO {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "이름", example = "아이스 바닐라라떼")
    private String name;

    @Schema(description = "가격", example = "5000")
    private int price;

    @Schema(description = "이미지 URL", example = "iceVanillaLatte.png")
    private String imageUrl;

    public static MenuUpdateOutDTO of(Menu menu) {
        return MenuUpdateOutDTO.builder()
                .menuId(menu.getId())
                .cafeId(menu.getCafe().getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
