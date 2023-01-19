package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@ApiModel(value = "리뷰 수정 Response DTO", description = "수정한 리뷰 내용")
@Builder(access = AccessLevel.PRIVATE)
public class PatchReviewRes {
    @ApiModelProperty(value = "리뷰 ID")
    private Long reviewId;

    @ApiModelProperty(value = "점수")
    private Integer score;

    @ApiModelProperty(value = "내용")
    private String content;

    @ApiModelProperty(value = "수정된 일시")
    private String updatedAt;

    public static PatchReviewRes of(Review review) {
        return PatchReviewRes.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
