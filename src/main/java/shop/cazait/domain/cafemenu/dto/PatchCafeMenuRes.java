package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "메뉴 수정 Response", description = "수정한 메뉴에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PatchCafeMenuRes {

    @ApiModelProperty(value = "메뉴 ID", example = "1")
    private Long cafeMenuId;

    @ApiModelProperty(value = "카페 ID", example = "1")
    private Long cafeId;

    @ApiModelProperty(value = "이름", example = "아이스 바닐라라떼")
    private String name;

    @ApiModelProperty(value = "가격", example = "5000")
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "iceVanillaLatte.png")
    private String imageUrl;

    public static PatchCafeMenuRes of(CafeMenu menu) {
        return PatchCafeMenuRes.builder()
                .cafeMenuId(menu.getId())
                .cafeId(menu.getCafe().getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
