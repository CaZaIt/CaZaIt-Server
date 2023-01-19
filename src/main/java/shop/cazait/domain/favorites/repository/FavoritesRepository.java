package shop.cazait.domain.favorites.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.favorites.entity.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<List<Favorites>> findAllByUserId(Long userId);

}
