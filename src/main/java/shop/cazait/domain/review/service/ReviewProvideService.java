package shop.cazait.domain.review.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.review.requestvalue.SortType;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    private final ReviewRepository reviewRepository;

    public GetReviewsRes getReviews(Long cafeId, String sortBy) {
        SortType sortType = SortType.of(sortBy);
        if (sortType == null) {
            sortType = SortType.NEWEST;
        }

        List<Review> reviews = reviewRepository.findAllByCafeId(
                cafeId,
                Sort.by(sortType.getDirection(), sortType.getProperty()));
        double averageScore = getAverageScore(reviews);

        return GetReviewsRes.of(averageScore, reviews);
    }

    private double getAverageScore(List<Review> reviews) {
        if (reviews.size() == 0) {  // 리뷰가 없는 경우 평점을 0점으로 처리
            return 0.0;
        }

        double averageScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .sum() / reviews.size();

        return averageScore;
    }

    public GetReviewRes getReview(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException());

        return GetReviewRes.of(review);
    }
}
