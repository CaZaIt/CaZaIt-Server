package shop.cazait.domain.cafemenu.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.cazait.domain.cafemenu.entity.CafeMenu;

public interface CafeMenuRepository extends JpaRepository<CafeMenu, Long> {


    Optional<List<CafeMenu>> findAllByCafeId(Long cafeId);

    Optional<CafeMenu> findByMenuAndCafe(Long cafeMenuId,Long cafeId);

}
