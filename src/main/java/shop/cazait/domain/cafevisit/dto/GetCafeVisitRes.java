package shop.cazait.domain.cafevisit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;

@NoArgsConstructor
@ApiModel(value = "카페 정보", description = "조회한 카페 정보")
public class GetCafeVisitRes {

    @ApiModelProperty(value = "카페 이름")
    private String name;

    @ApiModelProperty(value = "카페 이미지 URL")
    private String imageUrl;

    public GetCafeVisitRes(Cafe cafe) {
        this.name = cafe.getName();
        this.imageUrl = cafe.getImageUrl();
    }

}
