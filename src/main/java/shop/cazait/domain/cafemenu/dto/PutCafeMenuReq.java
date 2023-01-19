package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 수정", description = "수정할 메뉴에 대한 정보(단, 변경하지 않는 부분은 null, 숫자는 -1)")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PutCafeMenuReq {

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "이미지 URL")
    private String imageUrl;

}
