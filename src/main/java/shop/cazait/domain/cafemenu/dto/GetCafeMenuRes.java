package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "메뉴 조회  Response", description = "카페의 모든 메뉴에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeMenuRes {

    @ApiModelProperty(value = "메뉴 ID", example = "1")
    private Long cafeMenuId;

    @ApiModelProperty(value = "이름", example = "아이스 아메리카노")
    private String name;

    @ApiModelProperty(value = "가격", example = "4500")
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "iceAmericano.png")
    private String imageUrl;

    public static List<GetCafeMenuRes> of(List<CafeMenu> menus) {
        return menus.stream()
                .map(menu -> GetCafeMenuRes.builder()
                        .cafeMenuId(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build()).
                collect(Collectors.toList());
    }


}
