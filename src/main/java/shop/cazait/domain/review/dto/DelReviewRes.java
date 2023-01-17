package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class DelReviewRes {
    private long reviewId;

    public static DelReviewRes of(Review review) {
        return DelReviewRes.builder()
                .reviewId(review.getId())
                .build();
    }
}
