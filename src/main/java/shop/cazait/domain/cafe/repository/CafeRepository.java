package shop.cazait.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafe.entity.Cafe;

import java.util.List;

public interface CafeRepository extends JpaRepository <Cafe, Long> {

    List<Cafe> findByNameContainingIgnoreCase(String name);

}