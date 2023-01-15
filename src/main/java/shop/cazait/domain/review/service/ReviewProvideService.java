package shop.cazait.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;



@Service
@RequiredArgsConstructor
public class ReviewProvideService {
    private final ReviewRepository reviewRepository;

    public GetReviewsRes getReviews(long cafeId) {
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);

        GetReviewsRes getReviewsRes = GetReviewsRes.builder()
                .reviews(reviews)
                .averageScore(reviews.stream()
                        .mapToInt(Review::getScore)
                        .sum() / 5.0)
                .build();

        return getReviewsRes;
    }
}
