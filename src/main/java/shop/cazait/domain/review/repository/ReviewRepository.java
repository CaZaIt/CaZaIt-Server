package shop.cazait.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.entity.Review;



public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCafeId(Long cafeId);
    List<Review> findAllByCafeId(Long cafeId, Sort sort);
}
