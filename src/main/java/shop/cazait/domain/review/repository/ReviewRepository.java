package shop.cazait.domain.review.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.review.entity.Review;



public interface ReviewRepository extends JpaRepository<Review, UUID>, CustomReviewRepository {
    List<Review> findAllByCafeId(UUID cafeId);

    Slice<Review> findReviewByCafeId(UUID cafeId, Pageable pageable);
}
