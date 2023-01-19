package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "카페 메뉴 수정", description = "수정한 메뉴에 대한 정보")
@Builder(access = AccessLevel.PRIVATE)
public class PutCafeMenuRes {

    @ApiModelProperty("메뉴 ID")
    private Long cafeMenuId;

    @ApiModelProperty("카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "이미지 URL")
    private String imageUrl;

    public static PutCafeMenuRes of(CafeMenu menu) {
        return PutCafeMenuRes.builder()
                .cafeMenuId(menu.getId())
                .cafeId(menu.getCafe().getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
