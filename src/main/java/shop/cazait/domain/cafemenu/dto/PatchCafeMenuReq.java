package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchCafeMenuReq {

    @ApiParam(value = "이름", example = "아이스 아메리카노(미수정 : null)")
    private String name;

    @ApiModelProperty(value = "가격", example = "4500(미수정 : -1)")
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "iceAmericano.png(미수정 : null)")
    private String imageUrl;

}
