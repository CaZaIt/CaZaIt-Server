package shop.cazait.domain.cafe.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_MASTER;
import static shop.cazait.global.error.status.ErrorStatus.NOT_OPERATE_CAFE;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.GetCafesRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.dto.PostCafeRes;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.dto.GetCafeImageRes;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.domain.checklog.service.CheckLogService;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.domain.coordinate.entity.Coordinate;
import shop.cazait.domain.coordinate.service.CoordinateService;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.repository.FavoritesRepository;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.service.AwsS3Service;
import shop.cazait.global.common.status.BaseStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {

    private final CheckLogService checkLogService;
    private final CoordinateService coordinateService;
    private final CafeImageService cafeImageService;
    private final CafeRepository cafeRepository;
    private final MasterRepository masterRepository;
    private final FavoritesRepository favoritesRepository;

    /**
     * 카페 등록 좌표와 도로명 주소 받기 -> 카페 생성 -> 초기 혼잡도 등록 -> 이미지 S3 업로드 -> 이미지 객체  -> 마스터 계정에 카페 설정
     */
    public PostCafeRes addCafe(Long masterId, PostCafeReq cafeReq, List<MultipartFile> imageFiles)
            throws JsonProcessingException {

        // 좌표와 도로명 주소 받기
        Coordinate coordinate = coordinateService.getCoordinate(cafeReq);

        // 카페 생성
        Cafe cafe = Cafe.builder()
                .name(cafeReq.getName())
                .address(cafeReq.getAddress())
                .coordinate(coordinate)
                .build();

        // 초기 혼잡도 등록
        cafe = initCongestion(cafe);

        // 이미지 업로드 후 DB 저장
        cafeImageService.saveCafeImage(cafe, imageFiles);

        cafeRepository.save(cafe);

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));
        master.setCafe(cafe);

        List<GetCafeImageRes> getCafeImageResList = cafeImageService.readCafeImageList(cafe.getId());

        return PostCafeRes.of(cafe, getCafeImageResList);
    }

    private Cafe initCongestion(Cafe cafe) {
        Congestion congestion = Congestion.builder()
                .cafe(cafe)
                .congestionStatus(CongestionStatus.NONE)
                .build();
        cafe.initCongestion(congestion);
        return cafe;
    }

    /**
     * 카페 조회 (ACTIVE 상태)
     */
    @Transactional(readOnly = true)
    public List<List<GetCafesRes>> getCafeByStatusNoAuth(String longitude, String latitude, String sort, String limit) {
        List<Cafe> cafeList = cafeRepository.findAll();
        cafeList.removeIf(cafe -> cafe.getStatus() == BaseStatus.INACTIVE);
        List<GetCafesRes> getCafesRes = readCafeList(cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<GetCafesRes>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    @Transactional(readOnly = true)
    public List<List<GetCafesRes>> getCafeByStatus(Long userId, String longitude, String latitude, String sort, String limit) {
        List<Cafe> cafeList = cafeRepository.findAll();
        cafeList.removeIf(cafe -> cafe.getStatus() == BaseStatus.INACTIVE);
        List<GetCafesRes> getCafesRes = readCafeList(userId, cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<GetCafesRes>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    /**
     * 카페 상세 조회 (카페 id)
     */
    @Transactional(readOnly = true)
    public GetCafeRes getCafeByIdNoAuth(Long cafeId) throws CafeException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));
        List<GetCafeImageRes> getCafeImageResList = cafeImageService.readCafeImageList(cafeId);
        String logResult = "";    // 최근 본 카페 등록
        return GetCafeRes.of(cafe, getCafeImageResList, logResult);
    }

    @Transactional(readOnly = true)
    public GetCafeRes getCafeById(Long userId, Long cafeId) throws CafeException, UserException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));
        List<GetCafeImageRes> getCafeImageResList = cafeImageService.readCafeImageList(cafeId);
        String logResult = checkLogService.addVisitLog(userId, cafeId);    // 최근 본 카페 등록
        return GetCafeRes.of(cafe, getCafeImageResList, logResult);
    }

    /**
     * 카페 상세 조회 (카페 이름)
     */
    @Transactional(readOnly = true)
    public List<List<GetCafesRes>> getCafeByName(String name, Long userId, String longitude, String latitude, String sort, String limit) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findByNameContainingIgnoreCase(name);
        cafeList.removeIf(cafe -> cafe.getStatus() == BaseStatus.INACTIVE);
        List<GetCafesRes> getCafesRes = readCafeList(userId, cafeList, longitude, latitude);
        getCafesRes = sortCafeList(getCafesRes, sort, limit);
        List<List<GetCafesRes>> getCafesResList = pageCafeList(getCafesRes);
        return getCafesResList;
    }

    public PostCafeRes updateCafe(Long cafeId, Long masterId, PostCafeReq cafeReq)
            throws CafeException, JsonProcessingException {

        Coordinate coordinate = coordinateService.getCoordinate(cafeReq);

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));
        if (!(master.getCafe().getId().equals(cafe.getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }
        cafe.changeInfo(cafeReq, coordinate);
        cafeRepository.save(cafe);
        List<GetCafeImageRes> getCafeImageResList = cafeImageService.readCafeImageList(cafe.getId());

        return PostCafeRes.of(cafe, getCafeImageResList);
    }

    public void deleteCafe(Long cafeId, Long masterId) throws CafeException {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));
        if (!(master.getCafe().getId().equals(cafe.getId()))) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }
        cafe.changeCafeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);
    }

    private List<GetCafesRes> readCafeList(Long userId, List<Cafe> cafeList, String longitude, String latitude) {
        List<Favorites> favoritesList = favoritesRepository.findAllByUserId(userId).get();
        List<GetCafesRes> cafeResList = cafeList.stream()
                .map(cafe -> {
                    boolean favorite = false;
                    for (Favorites favorites : favoritesList) {
                        if (cafe.getId().equals(favorites.getCafe().getId())) {
                            favorite = true;
                            break;
                        }
                    }
                    List<GetCafeImageRes> getCafeImageResList = cafeImageService.readCafeImageList(cafe.getId());

                    int distance = DistanceService.distance(cafe.getCoordinate().getLatitude(),
                            cafe.getCoordinate().getLongitude(),
                            longitude, latitude);

                    return GetCafesRes.of(cafe, getCafeImageResList, distance, favorite);
                })
                .collect(Collectors.toList());
        return cafeResList;
    }

    private List<GetCafesRes> sortCafeList(List<GetCafesRes> getCafesRes, String sort, String limit) {
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

    private List<List<GetCafesRes>> pageCafeList(List<GetCafesRes> getCafesRes) {
        List<List<GetCafesRes>> getCafesResList = new ArrayList<>();
        int it = Math.min(5, getCafesRes.size() / 7 + 1);
        for (int i = 0;i < it;i++) {
            List<GetCafesRes> tmpGetCafesRes = new ArrayList<>();
            for (int j = 7 * i;j < 7 * (i + 1);j++) {
                if (getCafesRes.size() == j) {
                    break;
                }
                tmpGetCafesRes.add(getCafesRes.get(j));
            }
            getCafesResList.add(tmpGetCafesRes);
        }
        return getCafesResList;
    }
}

