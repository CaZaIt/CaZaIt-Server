package shop.cazait.domain.cafe.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInformation {

    private Long imageId;
    private String url;

    @Builder
    public ImageInformation(Long imageId, String url) {
        this.imageId = imageId;
        this.url = url;
    }


}
