package shop.cazait.domain.congestion.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.congestion.entity.Congestion;

public interface CongestionRepository extends JpaRepository<Congestion, Long> {

    Optional<Congestion> findByCafeId(Long cafeId);

}
