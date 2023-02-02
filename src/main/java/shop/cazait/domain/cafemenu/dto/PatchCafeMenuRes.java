package shop.cazait.domain.cafemenu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Schema(description = "메뉴 수정 Response : 수정한 메뉴에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PatchCafeMenuRes {

    @Schema(description = "메뉴 ID", example = "1")
    private Long cafeMenuId;

    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "이름", example = "아이스 바닐라라떼")
    private String name;

    @Schema(description = "가격", example = "5000")
    private int price;

    @Schema(description = "이미지 URL", example = "iceVanillaLatte.png")
    private String imageUrl;

    public static PatchCafeMenuRes of(CafeMenu menu) {
        return PatchCafeMenuRes.builder()
                .cafeMenuId(menu.getId())
                .cafeId(menu.getCafe().getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
