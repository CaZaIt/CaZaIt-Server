package shop.cazait.domain.favorites.service;

import static shop.cazait.domain.favorites.exception.FavoritesErrorStatus.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostFavoritesRes;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.favorites.repository.FavoritesRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    public PostFavoritesRes addFavorites(Long userId, Long cafeId) {

        Favorites favorites = Favorites.builder()
                .user(userRepository.findById(userId))
                .cafe(cafeRepository.findById(cafeId))
                .build();

        Long addFavoritesId = favoritesRepository.save(favorites).getId();

        return PostFavoritesRes.of(addFavoritesId);

    }

    @Transactional(readOnly = true)
    public List<GetFavoritesRes> getFavorites(Long userId) {

        List<Favorites> findFavorites = favoritesRepository.findAllByUserId(userId).get();

        return GetFavoritesRes.of(findFavorites);

    }

    public String deleteFavorites(Long favoritesId) {

        Favorites findFavorites = favoritesRepository
                .findById(favoritesId)
                .orElseThrow(() -> new FavoritesException(INVALID_CAFE_FAVORITES));

        favoritesRepository.delete(findFavorites);

        return "즐겨찾기 삭제 완료";

    }

}
