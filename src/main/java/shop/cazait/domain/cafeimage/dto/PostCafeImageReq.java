package shop.cazait.domain.cafeimage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "카페 이미지 Request : 카페 이미지 등록 시 필요한 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeImageReq {

    @Schema(description = "카페 이미지 링크", example = "americano.png")
    private String imageUrl;

    @Builder
    public PostCafeImageReq(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
