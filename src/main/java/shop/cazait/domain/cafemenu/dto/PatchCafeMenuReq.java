package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 메뉴 수정", description = "수정할 메뉴에 대한 정보(단, 변경하지 않는 부분은 null, 숫자는 -1)")
@Data
public class PatchCafeMenuReq {

    @ApiParam(value = "이름", example = "아이스 아메리카노(미수정 : null)")
    private String name;

    @ApiModelProperty(value = "가격", example = "4500(미수정 : -1)")
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "iceAmericano.png(미수정 : null)")
    private String imageUrl;

}
