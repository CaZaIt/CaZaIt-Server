package shop.cazait.domain.favorites.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.favorites.entity.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findAllByUserIdAndCafeId(UUID userId, Long cafeId);

    Optional<List<Favorites>> findAllByUserId(UUID userId);
}
