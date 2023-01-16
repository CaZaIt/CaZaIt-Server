package shop.cazait.domain.cafefavorites.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.cafefavorites.entity.CafeFavorites;

public interface CafeFavoritesRepository extends JpaRepository<CafeFavorites, Long> {

    List<CafeFavorites> findAllByUserId(Long userId);

     Optional<CafeFavorites> findCafeFavoritesByUserIdAndCafeId(Long userId, Long cafeId);
}
