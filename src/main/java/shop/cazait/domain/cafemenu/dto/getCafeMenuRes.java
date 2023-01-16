package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 조회", description = "메뉴 ID, 이름, 가격, 이미지를 담고 있는 Response 객체")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class getCafeMenuRes {

    @ApiModelProperty(value = "메뉴 ID")
    private Long cafeMenuId;

    @ApiModelProperty(value = "메뉴 이름")
    private String name;

    @ApiModelProperty(value = "메뉴 가격")
    private int price;

    @ApiModelProperty(value = "메뉴 이미지 주소")
    private String imageUrl;

    @Builder
    public getCafeMenuRes(Long cafeMenuId, String name, int price, String imageUrl) {
        this.cafeMenuId = cafeMenuId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
