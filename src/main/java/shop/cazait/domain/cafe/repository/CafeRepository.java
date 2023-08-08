package shop.cazait.domain.cafe.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafe.model.entity.Cafe;

import java.util.List;
import shop.cazait.domain.master.model.entity.Master;
import shop.cazait.global.common.status.BaseStatus;

public interface CafeRepository extends JpaRepository <Cafe, UUID> {

    List<Cafe >findAllByStatus(BaseStatus status);

    List<Cafe> findByNameContainingIgnoreCaseAndStatus(String name, BaseStatus status);

    Optional<List<Cafe>> findByMasterAndStatus(Master master, BaseStatus status);


}