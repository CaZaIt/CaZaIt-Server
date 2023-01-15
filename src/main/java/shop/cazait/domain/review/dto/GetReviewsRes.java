package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.review.entity.Review;


@Getter
@ApiModel(value = "리뷰들 및 평점", description = "해당 카페에 등록된 리뷰들 및 카페 평점")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetReviewsRes {
    @ApiModelProperty(value = "카페 평점")
    private double averageScore;
    @ApiModelProperty(value = "카페에 등록된 리뷰들")
    private List<Review> reviews;

    @Builder
    public GetReviewsRes(double averageScore, List<Review> reviews) {
        this.averageScore = averageScore;
        this.reviews = reviews;
    }
}
