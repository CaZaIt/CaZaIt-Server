package shop.cazait.domain.review.dto;

import lombok.Builder;
import lombok.Getter;



@Getter
public class PatchReviewReq {
    private final long reviewId;
    private final int score;
    private final String content;

    @Builder
    public PatchReviewReq(long reviewId, int score, String content) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
    }
}
