package shop.cazait.domain.review.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.review.entity.Review;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetReviewsRes {
    private double averageScore;
    private List<Review> reviews;

    @Builder
    public GetReviewsRes(double averageScore, List<Review> reviews) {
        this.averageScore = averageScore;
        this.reviews = reviews;
    }
}
