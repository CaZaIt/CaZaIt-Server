package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.review.entity.Review;



@ApiModel(value = "리뷰 하나 조회 Response DTO", description = "해당 리뷰의 정보")
@Builder(access = AccessLevel.PRIVATE)
public class GetReviewRes {
    @ApiModelProperty(value = "유저 ID")
    private Long userId;

    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "점수")
    private Integer score;

    @ApiModelProperty(value = "내용")
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
