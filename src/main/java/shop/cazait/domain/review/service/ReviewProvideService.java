package shop.cazait.domain.review.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_REVIEW;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.exception.ReviewException;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.review.requestvalue.SortType;
import shop.cazait.global.pagination.ScrollPaginationCollection;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    static final int COUNT_PER_SCROLL = 20;
    private final ReviewRepository reviewRepository;

    public Double getAverageScore(Long cafeId) {
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);

        Double averageScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);

        return Math.round(averageScore * 100) / 100.0;
    }

    public GetReviewsRes getReviews(Long cafeId, String sortBy, Integer score, Long lastId) {
        SortType sortType = SortType.of(sortBy);
        List<Review> reviews = null;

        if (reviewRepository.findTopByCafeId(cafeId) == null) {
            return null;
        }

        // ToDo: HashMap<SortType, Function>으로 함수 매핑시키기
        {
            if (sortType == SortType.NEWEST) {
                if (lastId == null) {
                    lastId = 0L;
                }
                reviews = reviewRepository.findNewestPageByCafeId(cafeId, score, lastId, COUNT_PER_SCROLL);
            } else if (sortType == sortType.OLDEST) {
                if (lastId == null) {
                    lastId = Long.MAX_VALUE;
                }
                reviews = reviewRepository.findOldestPageByCafeId(cafeId, score, lastId, COUNT_PER_SCROLL);
            }
        }

        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, COUNT_PER_SCROLL);

        return GetReviewsRes.of(reviewScroll);
    }


    public GetReviewRes getReviewDetail(Long reviewId) throws ReviewException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        return GetReviewRes.of(review);
    }
}
