package shop.cazait.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafe.entity.Cafe;

import java.util.List;
import shop.cazait.global.common.status.BaseStatus;

public interface CafeRepository extends JpaRepository <Cafe, Long> {

    List<Cafe >findAllByStatus(BaseStatus status);

    List<Cafe> findByNameContainingIgnoreCaseAndStatus(String name, BaseStatus status);

}