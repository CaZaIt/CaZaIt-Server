package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;



@ApiModel(value = "리뷰 수정 Request DTO", description = "리뷰 수정을 위해 필요한 정보")
@Getter
public class PatchReviewReq {
    @ApiModelProperty(value = "리뷰 ID")
    private final Long reviewId;
    
    @ApiModelProperty(value = "점수")
    private final Integer score;
    
    @ApiModelProperty(value = "리뷰 내용")
    private final String content;

    @Builder
    public PatchReviewReq(Long reviewId, Integer score, String content) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
    }
}
