package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;

@Schema(description = "리뷰 삭제 Response : 삭제한 리뷰의 ID")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DelReviewRes {

    @Schema(description = "리뷰 ID")
    private Long reviewId;

    public static DelReviewRes of(Review review) {
        return DelReviewRes.builder()
                .reviewId(review.getId())
                .build();
    }
}
