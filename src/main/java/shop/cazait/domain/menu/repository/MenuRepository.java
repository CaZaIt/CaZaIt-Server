package shop.cazait.domain.menu.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import shop.cazait.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {


    Optional<List<Menu>> findAllByCafeId(Long cafeId);

}
