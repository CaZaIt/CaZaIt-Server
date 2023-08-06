package shop.cazait.domain.review.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_REVIEW;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.review.dto.ReviewGetOutDTO;
import shop.cazait.domain.review.dto.ReviewsGetOutDTO;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.exception.ReviewException;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.review.requestvalue.SortType;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewProvideService {
    static final int COUNT_PER_SCROLL = 20;
    private final ReviewRepository reviewRepository;

    public Double getAverageScore(UUID cafeId) {
        List<Review> reviews = reviewRepository.findAllByCafeId(cafeId);

        Double averageScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);

        return Math.round(averageScore * 100) / 100.0;
    }

    public ReviewsGetOutDTO getReviews(UUID cafeId, int index, int nums, String sortBy, Integer score) {
        SortType sortType = SortType.of(sortBy);

        Slice<Review> reviews = reviewRepository.findReviewByCafeId(cafeId, PageRequest.of(index, nums));

        return ReviewsGetOutDTO.of(reviews);
    }


    public ReviewGetOutDTO getReviewDetail(UUID reviewId) throws ReviewException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        return ReviewGetOutDTO.of(review);
    }
}
