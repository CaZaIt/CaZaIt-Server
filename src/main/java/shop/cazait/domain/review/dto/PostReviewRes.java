package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@ApiModel(value = "리뷰 수정 Response DTO", description = "등록한 리뷰 내용")
@Builder(access = AccessLevel.PRIVATE)
public class PostReviewRes {
    @ApiModelProperty(value = "리뷰 ID")
    private Long reviewId;

    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "유저 ID")
    private Long userId;

    @ApiModelProperty(value = "점수")
    private Integer score;

    @ApiModelProperty(value = "내용")
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
