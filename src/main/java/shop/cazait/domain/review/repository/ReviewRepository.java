package shop.cazait.domain.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.review.entity.Review;



public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {
    List<Review> findAllByCafeId(Long cafeId);

    Review findTopByCafeId(Long cafeId);
}
