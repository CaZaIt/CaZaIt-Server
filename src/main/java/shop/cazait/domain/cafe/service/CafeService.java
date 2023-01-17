package shop.cazait.domain.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.global.common.status.BaseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;

    @Transactional(readOnly = false)
    public List<GetCafeRes> getCafeList(BaseStatus status) {
        List<Cafe> cafeList = cafeRepository.findAllByStatus(status);
        List<GetCafeRes> cafeResList = new ArrayList<>();
        for (Cafe cafe : cafeList) {
            GetCafeRes cafeRes = GetCafeRes.builder()
                    .cafeId(cafe.getId())
                    .congestionId(cafe.getCongestion().getId())
                    .name(cafe.getName())
                    .location(cafe.getLocation())
                    .longitude(cafe.getLongitude())
                    .latitude(cafe.getLatitude())
                    .build();
            cafeResList.add(cafeRes);
        }
        return cafeResList;
    }

}
