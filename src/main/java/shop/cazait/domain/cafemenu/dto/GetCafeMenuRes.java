package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "카페 메뉴 조회", description = "카페의 모든 메뉴에 대한 정보")
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeMenuRes {

    @ApiModelProperty(value = "ID")
    private Long cafeMenuId;

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "이미지 URL")
    private String imageUrl;

    public static List<GetCafeMenuRes> of(List<CafeMenu> Menus) {
        return Menus.stream()
                .map(Menu -> GetCafeMenuRes.builder()
                        .cafeMenuId(Menu.getId())
                        .name(Menu.getName())
                        .price(Menu.getPrice())
                        .imageUrl(Menu.getImageUrl())
                        .build()).
                collect(Collectors.toList());
    }


}
