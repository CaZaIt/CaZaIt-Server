package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;



@ApiModel(value = "전체 리뷰 조회 Response DTO", description = "해당 카페에 등록된 리뷰들 및 카페 평점")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetReviewsRes {
    @ApiModelProperty(value = "카페 평점")
    private double averageScore;

    @ApiModelProperty(value = "카페에 등록된 리뷰들의 DTO")
    private List<GetReviewRes> reviewDtos;


    public static GetReviewsRes of(double averageScore, List<Review> reviews) {
        List<GetReviewRes> reviewDtos = reviews.stream()
                .map(review -> GetReviewRes.of(review))
                .collect(Collectors.toList());

        return GetReviewsRes.builder()
                .averageScore(averageScore)
                .reviewDtos(reviewDtos)
                .build();
    }
}
