package shop.cazait.domain.review.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_REVIEW;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.exception.ReviewException;
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

    public List<GetReviewRes> getReviews(Long cafeId, String sortBy, Integer score) {
        SortType sortType = SortType.of(sortBy);

        // 특정 점수 조회가 아닐 때 (전체 조회)
        if (score == null) {
            return getReviewsWithNoScore(cafeId, sortType);
        }

        reviewRepository.findAll();
        return getReviewsWithScore(cafeId, score, sortType);

    }

    private List<GetReviewRes> getReviewsWithNoScore(Long cafeId, SortType sortType) {
        List<Review> reviews = reviewRepository.findAllByCafeId(
                cafeId,
                Sort.by(sortType.getDirection(), sortType.getProperty()));

        return reviews.stream()
                .map(review -> GetReviewRes.of(review))
                .collect(Collectors.toList());
    }

    private List<GetReviewRes> getReviewsWithScore(Long cafeId, Integer score, SortType sortType) {
        List<Review> reviewsWithScore = reviewRepository.findAllByCafeIdAndScore(
                cafeId,
                score,
                Sort.by(sortType.getDirection(), sortType.getProperty()));

        return reviewsWithScore.stream()
                .map(review -> GetReviewRes.of(review))
                .collect(Collectors.toList());
    }

    public GetReviewRes getReviewDetail(Long reviewId) throws ReviewException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        return GetReviewRes.of(review);
    }
}
