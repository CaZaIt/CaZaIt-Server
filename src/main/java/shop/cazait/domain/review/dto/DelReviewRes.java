package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;



@ApiModel(value = "리뷰 삭제 Response DTO", description = "삭제한 리뷰의 ID")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DelReviewRes {
    @ApiModelProperty(value = "리뷰 ID")
    private Long reviewId;

    public static DelReviewRes of(Review review) {
        return DelReviewRes.builder()
                .reviewId(review.getId())
                .build();
    }
}
