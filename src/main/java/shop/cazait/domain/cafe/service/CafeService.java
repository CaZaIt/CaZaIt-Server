package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.error.CafeErrorStatus;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.global.common.status.BaseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {

    private final CafeRepository cafeRepository;

    public void addCafe(PostCafeReq cafeReq) {
        Cafe cafe = Cafe.builder()
                .congestion(cafeReq.getCongestion())
                .name(cafeReq.getName())
                .location(cafeReq.getLocation())
                .longitude(cafeReq.getLongitude())
                .latitude(cafeReq.getLatitude())
                .build();

        if (cafeReq.getCongestion() == null) {
            Congestion tmp = Congestion.builder()
                    .cafe(cafe)
                    .status(CongestionStatus.FREE)
                    .build();
            cafe.initCafeCongestion(tmp);
        }

        cafeRepository.save(cafe);
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByStatus(BaseStatus status) throws CafeException {
        List<Cafe> cafeList = cafeRepository.findCafeByStatus(status);
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        if (cafeResList.isEmpty()) {
            throw new CafeException(CafeErrorStatus.NON_EXIST_CAFE);
        }
        return cafeResList;
    }

    @Transactional(readOnly = true)
    public GetCafeRes getCafeById(Long id) {
        Cafe cafe = cafeRepository.findById(id).get();
        GetCafeRes cafeRes = GetCafeRes.of(cafe);
        return cafeRes;
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByName(String name) {
        List<Cafe> cafeList = cafeRepository.findCafeByName(name);
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    public void updateCafe(Long id, PostCafeReq cafeReq) {
        Cafe cafe = cafeRepository.findById(id).get();
        cafe.changeCafeInfo(cafeReq);
        cafeRepository.save(cafe);
    }

    public void deleteCafe(Long id) {
        Cafe cafe = cafeRepository.findById(id).get();
        cafe.changeCafeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);
    }

}