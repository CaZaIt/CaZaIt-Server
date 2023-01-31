package shop.cazait.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.review.requestvalue.SortType;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    private final ReviewRepository reviewRepository;

    public Double getAverageScore(Long cafeId) {
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);

        Double averageScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);

        return Math.round(averageScore * 100) / 100.0;
    }

    public List<GetReviewRes> getReviews(Long cafeId, String sortBy) {
        SortType sortType = SortType.of(sortBy);

        List<Review> reviews = reviewRepository.findAllByCafeId(
                cafeId,
                Sort.by(sortType.getDirection(), sortType.getProperty()));

        return reviews.stream()
                .map(review -> GetReviewRes.of(review))
                .collect(Collectors.toList());
    }

    public GetReviewRes getReview(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException());

        return GetReviewRes.of(review);
    }
}
