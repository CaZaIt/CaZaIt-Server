package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class GetReviewRes {
    private final long userId;
    private final long cafeId;
    private final int score;
    private final String content;

    public static GetReviewRes from(Review review) {
        return GetReviewRes.builder()
                .userId(review.getUser().getId())
                .cafeId(review.getCafe().getId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
