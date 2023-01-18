package shop.cazait.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.review.requestvalue.SortType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    private static final double scoreDivider = 5.0;
    private final ReviewRepository reviewRepository;

    public GetReviewsRes getReviews(Long cafeId, String sortBy) {
        SortType sortType = SortType.of(sortBy);
        List<Review> reviews = reviewRepository.findAllByCafeId(
                cafeId,
                Sort.by(sortType.getDirection(), sortType.getColumn()));

        double averageScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .sum() / reviews.size();

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
