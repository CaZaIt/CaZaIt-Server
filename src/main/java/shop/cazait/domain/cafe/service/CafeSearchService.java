package shop.cazait.domain.cafe.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.model.dto.response.CafeGetOutDTO;
import shop.cazait.domain.cafe.model.dto.response.CafeListOutDTO;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.domain.coordinate.service.CoordinateService;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.repository.FavoritesRepository;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.status.BaseStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeSearchService {

    private final CoordinateService coordinateService;
    private final CafeImageService cafeImageService;

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;
    private final MasterRepository masterRepository;
    private final FavoritesRepository favoritesRepository;

    /**
     * 카페 조회 (ACTIVE 상태)
     */
    @Transactional(readOnly = true)
    public List<List<CafeListOutDTO>> findCafesByStatusNoAuth(String longitude, String latitude, String sort,
                                                              String limit) {
        List<Cafe> cafeList = cafeRepository.findAllByStatus(BaseStatus.ACTIVE);
        List<CafeListOutDTO> getCafesRes = readCafeList(cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<CafeListOutDTO>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    @Transactional(readOnly = true)
    public List<List<CafeListOutDTO>> findCafesByStatus(UUID userId, String longitude, String latitude, String sort,
                                                        String limit) {
        List<Cafe> cafeList = cafeRepository.findAllByStatus(BaseStatus.ACTIVE);
        List<CafeListOutDTO> getCafesRes = readCafeList(userId, cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<CafeListOutDTO>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    /**
     * 카페 상세 조회 (카페 id)
     */
    @Transactional(readOnly = true)
    public CafeGetOutDTO getCafeNoAuth(UUID cafeId) throws CafeException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new CafeException(NOT_EXIST_CAFE)
        );

        List<String> cafeImages = getImages(cafe.getId());

        return CafeGetOutDTO.of(cafe, cafeImages);
    }

    @Transactional(readOnly = true)
    public CafeGetOutDTO getCafe(UUID cafeId) throws CafeException, UserException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new CafeException(NOT_EXIST_CAFE)
        );
        List<String> cafeImages = getImages(cafe.getId());

        return CafeGetOutDTO.of(cafe, cafeImages);

    }

    /**
     * 카페 상세 조회 (카페 이름)
     */
    @Transactional(readOnly = true)
    public List<List<CafeListOutDTO>> findCafesByNameNoAuth(String name, String longitude, String latitude, String sort,
                                                            String limit) throws CafeException {

        List<Cafe> cafeList = cafeRepository.findByNameContainingIgnoreCaseAndStatus(name, BaseStatus.ACTIVE);

        List<CafeListOutDTO> getCafesRes = readCafeList(cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<CafeListOutDTO>> getCafesResList = pageCafeList(getCafesRes);

        return getCafesResList;
    }

    @Transactional(readOnly = true)
    public List<List<CafeListOutDTO>> findCafesByName(String name, UUID userId, String longitude, String latitude,
                                                      String sort, String limit) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findByNameContainingIgnoreCaseAndStatus(name, BaseStatus.ACTIVE);

        List<CafeListOutDTO> getCafesRes = readCafeList(userId, cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<CafeListOutDTO>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    private List<CafeListOutDTO> readCafeList(List<Cafe> cafeList, String longitude, String latitude) {
        List<CafeListOutDTO> cafeResList = cafeList.stream()
                .map(cafe -> {
                    boolean favorite = false;
                    List<String> cafeImages = getImages(cafe.getId());

                    int distance = DistanceService.getDistance(cafe.getCoordinate().getLatitude(),
                            cafe.getCoordinate().getLongitude(),
                            latitude, longitude);

                    return CafeListOutDTO.of(cafe, cafeImages, distance, favorite);
                })
                .collect(Collectors.toList());
        return cafeResList;
    }

    private List<CafeListOutDTO> readCafeList(UUID userId, List<Cafe> cafeList, String longitude, String latitude) {
        List<Favorites> favoritesList = favoritesRepository.findAllByUserId(userId).get();
        List<CafeListOutDTO> cafeResList = cafeList.stream()
                .map(cafe -> {
                    boolean favorite = false;
                    for (Favorites favorites : favoritesList) {
                        if (cafe.getId().equals(favorites.getCafe().getId())) {
                            favorite = true;
                            break;
                        }
                    }

                    List<String> cafeImages = getImages(cafe.getId());

                    int distance = DistanceService.getDistance(cafe.getCoordinate().getLatitude(),
                            cafe.getCoordinate().getLongitude(),
                            longitude, latitude);

                    return CafeListOutDTO.of(cafe, cafeImages, distance, favorite);
                })
                .collect(Collectors.toList());
        return cafeResList;
    }

    private List<CafeListOutDTO> sortCafeList(List<CafeListOutDTO> getCafesRes, String sort, String limit) {
        if (sort.equals("distance")) {
            getCafesRes.sort((c1, c2) -> c2.getDistance() - c1.getDistance());
            Collections.reverse(getCafesRes);
        } else {
            getCafesRes.sort((c1, c2) -> c2.getCongestionStatus().getLevel() - c1.getCongestionStatus().getLevel());
        }

        int distanceLimit = Integer.parseInt(limit);
        if (distanceLimit != 0) {
            getCafesRes.removeIf(cafesRes -> cafesRes.getDistance() > distanceLimit);
        }
        return getCafesRes;
    }

    private List<List<CafeListOutDTO>> pageCafeList(List<CafeListOutDTO> getCafesRes) {
        List<List<CafeListOutDTO>> getCafesResList = new ArrayList<>();
        int it = Math.min(5, getCafesRes.size() / 7 + 1);
        for (int i = 0; i < it; i++) {
            List<CafeListOutDTO> tmpGetCafesRes = new ArrayList<>();
            for (int j = 7 * i; j < 7 * (i + 1); j++) {
                if (getCafesRes.size() == j) {
                    break;
                }
                tmpGetCafesRes.add(getCafesRes.get(j));
            }
            getCafesResList.add(tmpGetCafesRes);
        }
        return getCafesResList;
    }

    private List<String> getImages(UUID cafeId) {
        List<String> cafeImages = cafeImageRepository.findByCafeId(cafeId)
                .stream()
                .map(cafeImage -> cafeImage.getImageUrl())
                .collect(Collectors.toList());
        return cafeImages;
    }

}
