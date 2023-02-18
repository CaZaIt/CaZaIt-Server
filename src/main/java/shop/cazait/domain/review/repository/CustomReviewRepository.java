package shop.cazait.domain.review.repository;

import java.util.List;
import shop.cazait.domain.review.entity.Review;



public interface CustomReviewRepository {
    List<Review> findNewestPageByCafeId(Long cafeId, Integer score, Long lastId, int size);

    List<Review> findOldestPageByCafeId(Long cafeId, Integer score, Long lastId, int size);
}
