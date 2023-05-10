package shop.cazait.domain.cafemenu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Schema(name = "메뉴 조회  Response", description = "카페의 모든 메뉴에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuListOutDTO {

    @Schema(description = "메뉴 ID", example = "1")
    private Long cafeMenuId;

    @Schema(description = "이름", example = "아이스 아메리카노")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "가격", example = "4500")
    private int price;

    @Schema(description = "이미지 URL", example = "iceAmericano.png")
    private String imageUrl;

    public static List<MenuListOutDTO> of(List<CafeMenu> menus) {
        return menus.stream()
                .map(menu -> MenuListOutDTO .builder()
                        .cafeMenuId(menu.getId())
                        .name(menu.getName())
                        .description(menu.getDescription())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build()).
                collect(Collectors.toList());
    }


}
