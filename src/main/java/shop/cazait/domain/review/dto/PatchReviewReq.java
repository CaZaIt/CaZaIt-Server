package shop.cazait.domain.review.dto;

import lombok.Builder;
import lombok.Getter;



@Getter
public class PatchReviewReq {
    private final Long reviewId;
    private final Integer score;
    private final String content;

    @Builder
    public PatchReviewReq(Long reviewId, Integer score, String content) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
    }
}
