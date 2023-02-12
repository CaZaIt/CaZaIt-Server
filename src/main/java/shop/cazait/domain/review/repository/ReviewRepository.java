package shop.cazait.domain.review.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.review.entity.Review;



public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCafeId(Long cafeId);

    Slice<Review> findAllByCafeIdAndIdLessThan(Long cafeId, Long lastId, PageRequest pageRequest);

    Slice<Review> findAllByCafeIdAndScoreAndIdLessThan(Long cafeId, Integer score, Long lastId,
                                                       PageRequest pageRequest);
}
