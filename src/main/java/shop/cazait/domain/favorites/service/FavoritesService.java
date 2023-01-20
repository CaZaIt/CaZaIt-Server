package shop.cazait.domain.favorites.service;

import static shop.cazait.domain.favorites.exception.FavoritesErrorStatus.*;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.error.CafeErrorStatus;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostFavoritesRes;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.favorites.repository.FavoritesRepository;
import shop.cazait.domain.user.entity.User;

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
    public PostFavoritesRes addFavorites(Long userId, Long cafeId) throws CafeException {

        User user = findUser(userId);
        Cafe cafe = findCafe(cafeId);

        Favorites favorites = Favorites.builder()
                .user(user)
                .cafe(cafe)
                .build();

        Long addFavoritesId = favoritesRepository.save(favorites).getId();

        return PostFavoritesRes.of(addFavoritesId);

    }

    private User findUser(Long userId) throws UserException {
        User findUser;
        try{
            findUser = userRepository.findById(userId).get();
        } catch (NoSuchElementException exception) {
            throw new UserException(UserErrorStauts.NON_EXIST_USER);
        }
        return findUser;
    }

    private Cafe findCafe(Long cafeId) throws CafeException {
        Cafe findCafe;
        try {
            findCafe = cafeRepository.findById(cafeId).get();
        } catch (NoSuchElementException exception) {
            throw new CafeException(CafeErrorStatus.NON_EXIST_CAFE);
        }
        return findCafe;
    }


    /**
     * 즐겨찾기 조회
     */
    @Transactional(readOnly = true)
    public List<GetFavoritesRes> getFavorites(Long userId) {

        List<Favorites> findFavorites = favoritesRepository.findAllByUserId(userId).orElse(null);

        return GetFavoritesRes.of(findFavorites);

    }

    /**
     * 즐겨찾기 삭제
     */
    public String deleteFavorites(Long favoritesId) {

        Favorites findFavorites = favoritesRepository
                .findById(favoritesId)
                .orElseThrow(() -> new FavoritesException(INVALID_CAFE_FAVORITES));

        favoritesRepository.delete(findFavorites);

        return "즐겨찾기 삭제 완료";

    }

}
