package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.review.entity.Review;


@Getter
public class PatchReviewReq {
    private long reviewId;
    private int score;
    private String content;

    @Builder
    public PatchReviewReq(long reviewId, int score, String content) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
    }
}
