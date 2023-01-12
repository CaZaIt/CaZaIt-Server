package shop.cazait.domain.cafefavorites.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

}
