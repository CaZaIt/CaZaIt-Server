package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.entity.Cafe;
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

    private final CafeRepository cafeRepository;

    public void addCafe(PostCafeReq cafeReq) {
        Cafe cafe = Cafe.builder()
                .name(cafeReq.getName())
                .location(cafeReq.getLocation())
                .longitude(cafeReq.getLongitude())
                .latitude(cafeReq.getLatitude())
                .build();

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
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    public void updateCafe(Long id, PostCafeReq cafeReq) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeCafeInfo(cafeReq);
        cafeRepository.save(cafe);
    }

    public void deleteCafe(Long id) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        cafe.changeCafeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);
    }

}
