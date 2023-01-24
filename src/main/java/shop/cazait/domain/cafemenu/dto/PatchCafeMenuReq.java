package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 수정", description = "수정할 메뉴에 대한 정보")
@Data
@NoArgsConstructor
public class PatchCafeMenuReq {

    @ApiModelProperty(value = "이름", example = "아이스 아메리카노(미수정 : null)")
    private String name;

    @ApiModelProperty(value = "가격", example = "4500(미수정 : -1)")
    private Integer price;

    @ApiModelProperty(value = "이미지 URL", example = "iceAmericano.png(미수정 : null)")
    private String imageUrl;

}
