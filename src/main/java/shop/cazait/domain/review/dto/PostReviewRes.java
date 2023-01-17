package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class PostReviewRes {
    private long reviewId;
    private long cafeId;
    private long userId;
    private int score;
    private String content;
    private String createdAt;

    public static PostReviewRes of(Review review) {
        return PostReviewRes.builder()
                .reviewId(review.getId())
                .cafeId(review.getCafe().getId())
                .userId(review.getUser().getId())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
