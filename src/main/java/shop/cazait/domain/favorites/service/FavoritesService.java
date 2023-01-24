package shop.cazait.domain.favorites.service;

import static shop.cazait.global.error.status.ErrorStatus.*;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostFavoritesRes;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.favorites.repository.FavoritesRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    /**
     * 즐겨찾기 추가
     */
    public PostFavoritesRes addFavorites(Long userId, Long cafeId) throws CafeException, UserException {

        User user = getUser(userId);
        Cafe cafe = getCafe(cafeId);

        Favorites favorites = Favorites.builder()
                .user(user)
                .cafe(cafe)
                .build();

        Long favoritesId = favoritesRepository.save(favorites).getId();

        return PostFavoritesRes.of(favoritesId);

    }

    private User getUser(Long userId) throws UserException {

        try{
            return userRepository.getReferenceById(userId);
        } catch (EntityNotFoundException exception) {
            throw new UserException(NOT_EXIST_CAFE);
        }

    }

    private Cafe getCafe(Long cafeId) throws CafeException {

        try {
            return cafeRepository.getReferenceById(cafeId);
        } catch (EntityNotFoundException exception) {
            throw new CafeException(NOT_EXIST_CAFE);
        }

    }


    /**
     * 즐겨찾기 조회
     */
    @Transactional(readOnly = true)
    public List<GetFavoritesRes> getFavorites(Long userId) {

        List<Favorites> favorites = favoritesRepository.findAllByUserId(userId).orElse(null);

        return GetFavoritesRes.of(favorites);

    }

    /**
     * 즐겨찾기 삭제
     */
    public String deleteFavorites(Long favoritesId) {

        Favorites favorites = favoritesRepository
                .findById(favoritesId)
                .orElseThrow(() -> new FavoritesException(INVALID_CAFE_FAVORITES));

        favoritesRepository.delete(favorites);

        return "즐겨찾기 삭제 완료";

    }

}
