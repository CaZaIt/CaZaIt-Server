package shop.cazait.domain.cafeimage.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafeimage.entity.CafeImage;

import java.util.List;

public interface CafeImageRepository extends JpaRepository<CafeImage, Long> {
    List<CafeImage> findByCafeId(UUID id);
}
