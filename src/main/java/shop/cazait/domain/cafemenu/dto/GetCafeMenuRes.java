package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "카페 메뉴 조회", description = "메뉴 ID, 이름, 가격, 이미지를 담고 있는 Response 객체")
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeMenuRes {

    @ApiModelProperty(value = "메뉴 ID")
    private Long cafeMenuId;

    @ApiModelProperty(value = "메뉴 이름")
    private String name;

    @ApiModelProperty(value = "메뉴 가격")
    private int price;

    @ApiModelProperty(value = "메뉴 이미지 주소")
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
