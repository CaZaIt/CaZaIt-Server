package shop.cazait.domain.cafeimage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "카페 이미지 Request", description = "카페 이미지 등록 시 필요한 정보")
@Getter
@NoArgsConstructor
public class PostCafeImageReq {
    @ApiModelProperty(value = "카페 이미지 링크", example = "americano.png")
    private String imageUrl;

    @Builder
    public PostCafeImageReq(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
