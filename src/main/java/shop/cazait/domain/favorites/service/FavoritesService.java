package shop.cazait.domain.favorites.service;

import static shop.cazait.domain.favorites.exception.FavoritesErrorStatus.*;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostCafeFavoritesRes;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.exception.CafeFavoritesException;
import shop.cazait.domain.favorites.repository.FavoritesRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final CafeRepositoy cafeRepositoy;
    private final UserRepositoy userRepositoy;

    public PostCafeFavoritesRes addFavorites(Long userId, Long cafeId) {

        Favorites favorites = Favorites.builder()
                .user(userRepositoy.findById(userId))
                .cafe(cafeRepositoy.findById(cafeId))
                .build();

        Long addFavoritesId = favoritesRepository.save(favorites).getId();

        return PostCafeFavoritesRes.of(addFavoritesId);

    }

    @Transactional(readOnly = true)
    public List<GetFavoritesRes> getFavorites(Long userId) {
        List<Favorites> findFavorites = favoritesRepository.findAllByUserId(userId);
        List<GetFavoritesRes> cafeFavoritesRes = findFavorites.stream()
                .map(favorites -> {
                    return GetFavoritesRes.builder()
                            .cafeId(favorites.getCafe().getId())
                            .name(favorites.getCafe().getName())
                            .imageUrl(favorites.getCafe().getImageUrl())
                            .build();
                }).collect(Collectors.toList());

        return cafeFavoritesRes;
    }

    public String deleteCafeFavorites(Long userId, Long cafeId) {

        Favorites findFavorites = favoritesRepository
                .findCafeFavoritesByUserIdAndCafeId(userId, cafeId)
                .orElseThrow(() -> new CafeFavoritesException(INVALID_CAFE_FAVORITES));

        favoritesRepository.delete(findFavorites);

        return "즐겨찾기 삭제가 완료 되었습니다.";

    }

}
