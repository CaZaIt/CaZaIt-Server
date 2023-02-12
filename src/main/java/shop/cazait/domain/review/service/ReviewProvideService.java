package shop.cazait.domain.review.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_REVIEW;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
        PageRequest pageRequest = PageRequest.of(0, 20 + 1,
                Sort.by(sortType.getDirection(), sortType.getProperty()));  // 다음 스크롤 유무를 확인하기 위해 + 1

        // TODO: ID 오름차순 정렬 결과 확인하기
        // 특정 점수 조회가 아닐 때 (전체 조회)
        if (score == null) {
            return getReviewsWithNoScore(cafeId, lastId, pageRequest);
        }

        // TODO: 특정 score의 결과 확인하기
        return getReviewsWithScore(cafeId, score, lastId, pageRequest);
    }

    private GetReviewsRes getReviewsWithNoScore(Long cafeId, Long lastId, PageRequest pageRequest) {
        Slice<Review> reviewScroll = reviewRepository.findAllByCafeIdAndLastIdLessThan(
                cafeId,
                lastId,
                pageRequest);

        return getReviewsResFromPage(reviewScroll);
    }

    private GetReviewsRes getReviewsWithScore(Long cafeId, Integer score, Long lastId, PageRequest pageRequest) {
        Slice<Review> reviewScroll = reviewRepository.findAllByCafeIdAndScoreAndLastIdLessThan(
                cafeId,
                score,
                lastId,
                pageRequest);

        return getReviewsResFromPage(reviewScroll);
    }

    private GetReviewsRes getReviewsResFromPage(Slice<Review> reviewScroll) {
        List<Review> reviews = reviewScroll.getContent();

        ScrollPaginationCollection<Review> reviewsCursor = ScrollPaginationCollection.of(reviews, 20 + 1);
        GetReviewsRes getReviewsRes = GetReviewsRes.of(reviewsCursor, 20L);

        if (getReviewsRes.getReviewResponses().isEmpty()) {
            return null;
        }

        return getReviewsRes;
    }

    public GetReviewRes getReviewDetail(Long reviewId) throws ReviewException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        return GetReviewRes.of(review);
    }
}
