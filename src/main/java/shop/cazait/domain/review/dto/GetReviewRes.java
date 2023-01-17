package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class GetReviewRes {
    private final Long userId;
    private final Long cafeId;
    private final Integer score;
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
