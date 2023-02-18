package shop.cazait.domain.review.repository;


import static org.assertj.core.api.Assertions.assertThat;

import config.RepositoryTestConfig;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.global.pagination.ScrollPaginationCollection;



@DataJpaTest
@Import(RepositoryTestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    private final static int SIZE = 5;

    @Test
    public void 리뷰_최신순_첫_조회_점수X() {
        List<Review> reviews = reviewRepository.findNewestPageByCafeId(1L, null, 0L, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        assertThat(reviews.get(0).getId()).isEqualTo(1L);
        assertThat(reviews.size()).isEqualTo(SIZE + 1);
        assertThat(reviewScroll.isLastScroll()).isFalse();
    }

    @Test
    public void 리뷰_최신순_첫_조회_점수O() {
        List<Review> reviews = reviewRepository.findNewestPageByCafeId(1L, 5, 0L, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        List<Integer> scores = reviews.stream()
                .map(review -> review.getScore())
                .collect(Collectors.toList());

        assertThat(scores).containsOnly(5);
        assertThat(scores).doesNotContain(1, 2, 3, 4);
        assertThat(reviewScroll.isLastScroll()).isFalse();
    }

    @Test
    public void 리뷰_최신순_마지막_조회_점수X() {
        List<Review> allReviews = reviewRepository.findAllByCafeId(1L);
        long lastId = allReviews.get(allReviews.size() - 1).getId();

        List<Review> reviews = reviewRepository.findNewestPageByCafeId(1L, null, lastId, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        GetReviewsRes getReviewsRes = GetReviewsRes.of(reviewScroll);

        assertThat(reviews).isEmpty();
        assertThat(reviewScroll.isLastScroll()).isTrue();
        assertThat(getReviewsRes.getTotalElements()).isEqualTo(0);
    }

    @Test
    public void 리뷰_최신순_마지막_조회_점수O() {
        List<Review> allReviews = reviewRepository.findAllByCafeId(1L);
        long lastId = allReviews.get(allReviews.size() - 1).getId();

        List<Review> reviews = reviewRepository.findNewestPageByCafeId(1L, 5, lastId, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        GetReviewsRes getReviewsRes = GetReviewsRes.of(reviewScroll);

        assertThat(reviews).isEmpty();
        assertThat(reviewScroll.isLastScroll()).isTrue();
        assertThat(getReviewsRes.getTotalElements()).isEqualTo(0);
    }

    @Test
    public void 리뷰_오래된순_첫_조회_점수X() {
        List<Review> allReviews = reviewRepository.findAllByCafeId(1L);
        long lastId = allReviews.get(allReviews.size() - 1).getId();

        List<Review> reviews = reviewRepository.findOldestPageByCafeId(1L, null, 10000L, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        GetReviewsRes getReviewsRes = GetReviewsRes.of(reviewScroll);

        assertThat(reviews.get(0).getId()).isEqualTo(lastId);
        assertThat(reviews.size()).isEqualTo(SIZE + 1);
        assertThat(reviewScroll.isLastScroll()).isFalse();
        assertThat(getReviewsRes.getTotalElements()).isEqualTo(SIZE);
    }

    @Test
    public void 리뷰_오래된순_첫_조회_점수O() {
        List<Review> reviews = reviewRepository.findOldestPageByCafeId(1L, 5, 10000L, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        assertThat(reviews.size()).isEqualTo(SIZE + 1);
        assertThat(reviewScroll.isLastScroll()).isFalse();

        List<Integer> scores = reviews.stream()
                .map(review -> review.getScore())
                .collect(Collectors.toList());

        assertThat(scores).containsOnly(5);
        assertThat(scores).doesNotContain(1, 2, 3, 4);
    }

    @Test
    public void 리뷰_오래된순_마지막_조회() {
        List<Review> reviews = reviewRepository.findOldestPageByCafeId(1L, null, 1L, SIZE);
        ScrollPaginationCollection<Review> reviewScroll = ScrollPaginationCollection.of(reviews, SIZE);

        assertThat(reviews).isEmpty();
        assertThat(reviewScroll.isLastScroll()).isTrue();
    }
}