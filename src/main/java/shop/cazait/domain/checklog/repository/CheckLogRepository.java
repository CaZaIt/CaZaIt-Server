package shop.cazait.domain.checklog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.checklog.entity.CheckLog;

@Repository
public interface CheckLogRepository extends JpaRepository<CheckLog, Long> {

    Optional<List<CheckLog>> findCafeVisitsByUserId(Long userId);

}
