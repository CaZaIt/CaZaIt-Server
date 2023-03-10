package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;

@Schema(description = "리뷰 수정 Response : 등록한 리뷰 내용")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostReviewRes {
    @Schema(description = "리뷰 ID")
    private Long reviewId;

    @Schema(description = "카페 ID")
    private Long cafeId;

    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "점수")
    private Integer score;

    @Schema(description = "내용")
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
