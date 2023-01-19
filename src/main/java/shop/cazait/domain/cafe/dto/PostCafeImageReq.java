package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "PostCafeImageReq / 카페 이미지 정보", description = "카페 이미지 등록 시 필요한 dto")
@Getter
@JsonAutoDetect
public class PostCafeImageReq {
    @ApiModelProperty(value = "카페 이미지 링크", example = "dkfsowjhdlv")
    private String imageUrl;

    public PostCafeImageReq() {

    }

    @Builder
    public PostCafeImageReq(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
