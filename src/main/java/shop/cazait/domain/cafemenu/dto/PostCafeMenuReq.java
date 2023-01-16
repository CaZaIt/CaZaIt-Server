package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 등록", description = "메뉴 이름, 가격, 이미지를 담고 있는 Request 객체")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCafeMenuReq {

    @ApiModelProperty(value = "메뉴 이름")
    private String name;

    @ApiModelProperty(value = "메뉴 가격")
    private int price;

    @ApiModelProperty(value = "메뉴 이미지 주소")
    private String imageUrl;

}
