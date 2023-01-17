package shop.cazait.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository <Cafe, Long> {

    List<Cafe> findCafeByStatus(BaseStatus status);
    Optional<Cafe> findCafeById(Long id);
    List<Cafe> findCafeByName(String name);

}