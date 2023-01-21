package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.global.common.status.BaseStatus;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    .congestionStatus(CongestionStatus.FREE)
                    .build();
            cafe.initCafeCongestion(tmp);
        }

        cafeRepository.save(cafe);
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByStatus(BaseStatus status) throws BaseException {
        List<Cafe> cafeList = cafeRepository.findCafeByStatus(status);
        if (cafeList.size() == 0) {
            throw new BaseException(ErrorStatus.NON_EXIST_CAFE);
        }
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    @Transactional(readOnly = true)
    public GetCafeRes getCafeById(Long id) throws BaseException {
        Optional<Cafe> cafe = cafeRepository.findById(id);
        if (cafe.isPresent()) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe.get());
            return cafeRes;
        }
        else {
            throw new BaseException(ErrorStatus.INVALID_CAFE_ID);
        }
    }

    @Transactional(readOnly = true)
    public List<GetCafeRes> getCafeByName(String name) throws BaseException {
        List<Cafe> cafeList = cafeRepository.findCafeByName(name);
        if (cafeList.size() == 0) {
            throw new BaseException(ErrorStatus.INVALID_CAFE_NAME);
        }
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.of(cafe);
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

    public void updateCafe(Long id, PostCafeReq cafeReq) throws BaseException {
        Optional<Cafe> cafe = cafeRepository.findById(id);
        if (cafe.isPresent()) {
            Cafe uCafe = cafe.get();
            uCafe.changeCafeInfo(cafeReq);
            cafeRepository.save(uCafe);
        }
        else {
            throw new BaseException(ErrorStatus.INVALID_CAFE_ID);
        }
    }

    public void deleteCafe(Long id) throws BaseException {
        Optional<Cafe> cafe = cafeRepository.findById(id);
        if (cafe.isPresent()) {
            Cafe uCafe = cafe.get();
            uCafe.changeCafeStatus(BaseStatus.INACTIVE);
            cafeRepository.save(uCafe);
        }
        else {
            throw new BaseException(ErrorStatus.INVALID_CAFE_ID);
        }
    }

}
