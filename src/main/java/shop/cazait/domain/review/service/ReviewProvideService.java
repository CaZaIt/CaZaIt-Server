package shop.cazait.domain.review.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    private static final double scoreDivider = 5.0;
    private final ReviewRepository reviewRepository;

    public GetReviewsRes getReviews(Long cafeId) {
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);
        double averageScore = reviews.stream()
                .mapToInt(Review::getScore)
                .sum() / scoreDivider;

        return GetReviewsRes.of(averageScore, reviews);
    }

    public GetReviewRes getReview(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);

        try {
            Review review = reviewOptional.get();
            return GetReviewRes.of(review);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
