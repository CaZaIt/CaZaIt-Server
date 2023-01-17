package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class GetReviewRes {
    private Long userId;
    private Long cafeId;
    private Integer score;
    private String content;

    public static GetReviewRes of(Review review) {
        return GetReviewRes.builder()
                .userId(review.getUser().getId())
                .cafeId(review.getCafe().getId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
