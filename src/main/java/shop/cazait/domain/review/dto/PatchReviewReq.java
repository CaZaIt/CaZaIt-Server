package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@ApiModel(value = "리뷰 수정 Request DTO", description = "리뷰 수정을 위해 필요한 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchReviewReq {
    @ApiModelProperty(value = "리뷰 ID", example = "1")
    @NotNull(message = "존재하지 않는 리뷰입니다.")
    private Long reviewId;

    @ApiModelProperty(value = "점수", example = "4")
    @NotNull(message = "점수를 입력해주세요.")
    @Min(value = 1, message = "점수는 1점 이상이여야합니다.")
    @Max(value = 5, message = "점수는 5점 이하여야합니다.")
    private Integer score;

    @ApiModelProperty(value = "리뷰 내용", example = "음식이 친절하고 사장님이 맛있어요 ^^")
    private String content;

    @Builder
    public PatchReviewReq(Long reviewId, Integer score, String content) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
    }
}
