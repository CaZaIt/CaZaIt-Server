package shop.cazait.domain.cafevisit.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.cafevisit.entity.CafeVisit;

@Repository
public interface CafeVisitRepository extends JpaRepository<CafeVisit, Long> {

    List<CafeVisit> findCafeVisitById(Long id);

    List<CafeVisit> findCafeVisitsByUserId(Long userId);

}
