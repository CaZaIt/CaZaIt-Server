package shop.cazait.domain.cafefavorites.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafefavorites.dto.GetCafeFavoritesRes;
import shop.cazait.domain.cafefavorites.entity.CafeFavorites;
import shop.cazait.domain.cafefavorites.repository.CafeFavoritesRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeFavoritesService {

    private final CafeFavoritesRepository cafeFavoritesRepository;
    private final CafeRepositoy cafeRepositoy;
    private final UserRepositoy userRepositoy;

    @Transactional
    public Long addCafeFavorites(Long userId, Long cafeId) {

        CafeFavorites cafeFavorites = CafeFavorites.builder()
                .user(userRepositoy.findById(userId))
                .cafe(cafeRepositoy.findById(cafeId))
                .build();

        Long cafeFavoritesId = cafeFavoritesRepository.save(cafeFavorites).getId();

        return cafeFavoritesId;

    }

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

}
