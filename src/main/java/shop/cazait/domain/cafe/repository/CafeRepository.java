package shop.cazait.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;

public interface CafeRepository extends JpaRepository <Cafe, Long> {

    List<Cafe> findByStatus(BaseStatus status);
    List<Cafe> findByNameContainingIgnoreCase(String name);

}