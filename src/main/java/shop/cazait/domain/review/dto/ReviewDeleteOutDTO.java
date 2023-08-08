package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;



@Schema(description = "리뷰 삭제 Response : 삭제한 리뷰의 ID")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewDeleteOutDTO {

    @Schema(description = "리뷰 ID")
    private UUID reviewId;

    public static ReviewDeleteOutDTO of(Review review) {
        return ReviewDeleteOutDTO.builder()
                .reviewId(review.getId())
                .build();
    }
}
