package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 수정", description = "메뉴 이름, 가격, 이미지를 담고 있는 Response 객체. 변경하지 않는 부분은 NULL, 숫자는 -1")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PutCafeMenuReq {

    @ApiModelProperty(value = "메뉴 이름")
    private String name;

    @ApiModelProperty(value = "메뉴 가격")
    private int price;

    @ApiModelProperty(value = "메뉴 이미지 주소")
    private String imageUrl;

}
