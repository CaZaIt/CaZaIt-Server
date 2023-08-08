package shop.cazait.domain.review.repository;

import java.util.List;
import java.util.UUID;
import shop.cazait.domain.review.entity.Review;



public interface CustomReviewRepository {
    List<Review> findNewestPageByCafeId(UUID cafeId, Integer score, Long lastId, int size);

    List<Review> findOldestPageByCafeId(UUID cafeId, Integer score, Long lastId, int size);
}
