package shop.cazait.domain.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@Builder(access = AccessLevel.PRIVATE)
public class PostReviewRes {
    private Long reviewId;
    private Long cafeId;
    private Long userId;
    private Integer score;
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
