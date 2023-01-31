package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.CoordinateVO;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.entity.Coordinate;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
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

    public void addCafe(PostCafeReq cafeReq) {

        CoordinateVO coordinateVO = coordinateService.getCoordinateFromAddress(cafeReq.getAddress());
        Coordinate coordinate = Coordinate.builder()
                .longitude(coordinateVO.getDocuments().getX())
                .latitude(coordinateVO.getDocuments().getY())
                .build();

        Cafe cafe = Cafe.builder()
                .name(cafeReq.getName())
                .address(cafeReq.getAddress())
                .coordinate(coordinate)
                .build();

        // todo: CongestionStatus default값(ex.None)으로 수정
        Congestion tmp = Congestion.builder()
                .cafe(cafe)
                .congestionStatus(CongestionStatus.FREE)
                .build();
        cafe.initCafeCongestion(tmp);

        cafeRepository.save(cafe);
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByStatus(BaseStatus status) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findByStatus(status);
        if (cafeList.size() == 0) {
            throw new CafeException(ErrorStatus.NOT_EXIST_CAFE);
        }
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    @Transactional(readOnly = true)
    public GetCafeRes getCafeById(Long id) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        GetCafeRes cafeRes = GetCafeRes.of(cafe);
        return cafeRes;
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByName(String name) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findByNameContainingIgnoreCase(name);
        if (cafeList.size() == 0) {
            throw new CafeException(ErrorStatus.INVALID_CAFE_NAME);
        }
        cafeList.removeIf(cafe -> cafe.getStatus() == BaseStatus.INACTIVE);
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    public void updateCafe(Long id, PostCafeReq cafeReq) throws CafeException {
        CoordinateVO coordinateVO = coordinateService.getCoordinateFromAddress(cafeReq.getAddress());
        Coordinate coordinate = Coordinate.builder()
                .longitude(coordinateVO.getDocuments().getX())
                .latitude(coordinateVO.getDocuments().getY())
                .build();

        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeCafeInfo(cafeReq, coordinate);
        cafeRepository.save(cafe);
    }

    public void deleteCafe(Long id) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeCafeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);
    }

}
