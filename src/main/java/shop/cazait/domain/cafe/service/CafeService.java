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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;

    public List<GetCafeRes> getCafeList(BaseStatus status) {
        List<Cafe> cafeList = cafeRepository.findCafeByStatus(status);
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

    public GetCafeRes findCafeById(Long id) {
        Optional<Cafe> cafe = cafeRepository.findCafeById(id);
        GetCafeRes cafeRes = GetCafeRes.builder()
                .cafeId(cafe.get().getId())
                .congestionId(cafe.get().getCongestion().getId())
                .name(cafe.get().getName())
                .location(cafe.get().getLocation())
                .longitude(cafe.get().getLongitude())
                .latitude(cafe.get().getLatitude())
                .build();
        return cafeRes;
    }

    public List<GetCafeRes> findCafeByName(String name) {
        List<Cafe> cafeList = cafeRepository.findCafeByName(name);
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
