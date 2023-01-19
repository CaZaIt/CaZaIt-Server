package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class PostCafeImageReq {
    private String imageUrl;

    public PostCafeImageReq() {

    }

    @Builder
    public PostCafeImageReq(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
