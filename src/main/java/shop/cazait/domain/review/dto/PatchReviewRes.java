package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class PatchReviewRes {
    private long reviewId;
    private int score;
    private String content;
    private String updatedAt;

    public static PatchReviewRes of(Review review) {
        return PatchReviewRes.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
