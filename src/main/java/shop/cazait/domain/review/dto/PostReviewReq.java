package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;



@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReviewReq {
    private long userId;
    private long cafeId;
    private int score;
    private String content;

    @Builder
    public PostReviewReq(long userId, long cafeId, int score, String content) {
        this.userId = userId;
        this.cafeId = cafeId;
        this.score = score;
        this.content = content;
    }
}
