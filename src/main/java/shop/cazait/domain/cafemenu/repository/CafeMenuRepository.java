package shop.cazait.domain.cafemenu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.cazait.domain.cafemenu.entity.CafeMenu;

public interface CafeMenuRepository extends JpaRepository<CafeMenu, Long> {

    List<CafeMenu> findAllByCafeId(Long cafeId);

    CafeMenu findByMenuAndCafe(Long cafeMenuId,Long cafeId);

}
