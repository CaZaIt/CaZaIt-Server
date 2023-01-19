package shop.cazait.domain.cafefavorites.service;

import static shop.cazait.domain.cafefavorites.exception.CafeFavoritesErrorStatus.*;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafefavorites.dto.GetCafeFavoritesRes;
import shop.cazait.domain.cafefavorites.dto.PostCafeFavoritesRes;
import shop.cazait.domain.cafefavorites.entity.CafeFavorites;
import shop.cazait.domain.cafefavorites.exception.CafeFavoritesErrorStatus;
import shop.cazait.domain.cafefavorites.exception.CafeFavoritesException;
import shop.cazait.domain.cafefavorites.repository.CafeFavoritesRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeFavoritesService {

    private final CafeFavoritesRepository cafeFavoritesRepository;
    private final CafeRepositoy cafeRepositoy;
    private final UserRepositoy userRepositoy;

    public PostCafeFavoritesRes addCafeFavorites(Long userId, Long cafeId) {

        CafeFavorites cafeFavorites = CafeFavorites.builder()
                .user(userRepositoy.findById(userId))
                .cafe(cafeRepositoy.findById(cafeId))
                .build();

        Long cafeFavoritesId = cafeFavoritesRepository.save(cafeFavorites).getId();

        return PostCafeFavoritesRes.builder()
                .id(cafeFavoritesId)
                .build();

    }

    @Transactional(readOnly = true)
    public List<GetCafeFavoritesRes> getCafeFavorites(Long userId) {
        List<CafeFavorites> findCafeFavorites = cafeFavoritesRepository.findAllByUserId(userId);
        List<GetCafeFavoritesRes> cafeFavoritesRes = findCafeFavorites.stream()
                .map(cafeFavorites -> {
                    return GetCafeFavoritesRes.builder()
                            .cafeId(cafeFavorites.getCafe().getId())
                            .name(cafeFavorites.getCafe().getName())
                            .imageUrl(cafeFavorites.getCafe().getImageUrl())
                            .build();
                }).collect(Collectors.toList());

        return cafeFavoritesRes;
    }

    public String deleteCafeFavorites(Long userId, Long cafeId) {

        CafeFavorites findCafeFavorites = cafeFavoritesRepository
                .findCafeFavoritesByUserIdAndCafeId(userId, cafeId)
                .orElseThrow(() -> new CafeFavoritesException(INVALID_CAFE_FAVORITES));

        cafeFavoritesRepository.delete(findCafeFavorites);

        return "즐겨찾기 삭제가 완료 되었습니다.";

    }

}
