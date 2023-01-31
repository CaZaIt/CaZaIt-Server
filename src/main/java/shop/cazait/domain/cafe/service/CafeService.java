package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.GetCafesRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.entity.Coordinate;
import shop.cazait.domain.cafe.entity.SortType;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.checklog.service.CheckLogService;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.error.status.ErrorStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {

    private final CoordinateService coordinateService;
    private final CafeRepository cafeRepository;
    private final CheckLogService checkLogService;

    public void addCafe(PostCafeReq cafeReq) {

//        CoordinateVO coordinateVO = coordinateService.getCoordinateFromAddress(cafeReq.getAddress());
//        Coordinate coordinate = Coordinate.builder()
//                .longitude(coordinateVO.getDocuments().getX())
//                .latitude(coordinateVO.getDocuments().getY())
//                .build();

        // todo: 지우기
        Coordinate coordinate = Coordinate.builder()
                .longitude("127.546123")
                .latitude("35.8489513")
                .build();

        Cafe cafe = Cafe.builder()
                .name(cafeReq.getName())    
                .address(cafeReq.getAddress())
                .coordinate(coordinate)
                .build();

        Congestion tmp = Congestion.builder()
                .cafe(cafe)
                .congestionStatus(CongestionStatus.NONE)
                .build();
        cafe.initCongestion(tmp);

        cafeRepository.save(cafe);
    }

    @Transactional(readOnly = true)
    public List<GetCafesRes> getCafeByStatus(BaseStatus status) throws CafeException {
//        SortType sortType = SortType.of("congestion");
//        List<Cafe> cafeList = cafeRepository.findByStatus(status, Sort.by(sortType.getDirection(), sortType.getProperty()));
        List<Cafe> cafeList = cafeRepository.findByStatus(status);
        if (cafeList.size() == 0) {
            throw new CafeException(ErrorStatus.NOT_EXIST_CAFE);
        }
        List<GetCafesRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafesRes cafeRes = GetCafesRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    @Transactional(readOnly = true)
    public GetCafeRes getCafeById(Long userId, Long cafeId) throws CafeException, UserException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        String logResult = checkLogService.addVisitLog(userId, cafeId);    // 최근 본 카페 등록
        return GetCafeRes.of(cafe, logResult);
    }

    @Transactional(readOnly = true)
    public List<GetCafesRes> getCafeByName(String name) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findByNameContainingIgnoreCase(name);
        if (cafeList.size() == 0) {
            throw new CafeException(ErrorStatus.INVALID_CAFE_NAME);
        }
        cafeList.removeIf(cafe -> cafe.getStatus() == BaseStatus.INACTIVE);
        List<GetCafesRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafesRes cafeRes = GetCafesRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    public void updateCafe(Long id, PostCafeReq cafeReq) throws CafeException {
//        CoordinateVO coordinateVO = coordinateService.getCoordinateFromAddress(cafeReq.getAddress());
//        Coordinate coordinate = Coordinate.builder()
//                .longitude(coordinateVO.getDocuments().getX())
//                .latitude(coordinateVO.getDocuments().getY())
//                .build();

        // todo: 지우기
        Coordinate coordinate = Coordinate.builder()
                .longitude("125.549813")
                .latitude("83.54128")
                .build();

        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeInfo(cafeReq, coordinate);
        cafeRepository.save(cafe);
    }

    public void deleteCafe(Long id) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeCafeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);
    }

}
