package shop.cazait.domain.cafemenu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Schema(name = "등록 메뉴 Response", description = "등록을 완료한 메뉴 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuCreateOutDTO {

    @Schema(description = "ID", example = "1")
    private Long menuId;

    @Schema(description = "이름", example = "아메리카노")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "가격", example = "3000")
    private int price;

    @Schema(description = "이미지 URL", example = "americano.png")
    private String imageUrl;

    public static MenuCreateOutDTO of(CafeMenu menu) {

        return MenuCreateOutDTO.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }

}
