package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "카페 메뉴 수정", description = "메뉴 이름, 가격, 이미지를 담고 있는 Response 객체. 변경하지 않는 부분은 NULL, 숫자는 -1")
@Builder(access = AccessLevel.PRIVATE)
public class PutCafeMenuRes {

    @ApiModelProperty("메뉴 ID")
    private Long cafeMenuId;

    @ApiModelProperty("카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "메뉴 이름")
    private String name;

    @ApiModelProperty(value = "메뉴 가격")
    private int price;

    @ApiModelProperty(value = "메뉴 이미지 주소")
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
