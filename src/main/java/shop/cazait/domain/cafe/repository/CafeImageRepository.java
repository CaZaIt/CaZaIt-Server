package shop.cazait.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafe.entity.CafeImage;

public interface CafeImageRepository extends JpaRepository<CafeImage, Long> {

}
