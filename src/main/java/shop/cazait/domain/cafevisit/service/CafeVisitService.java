package shop.cazait.domain.cafevisit.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafevisit.dto.GetCafeVisitRes;
import shop.cazait.domain.cafevisit.entity.CafeVisit;
import shop.cazait.domain.cafevisit.repository.CafeVisitRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeVisitService {

    private final CafeVisitRepository cafeVisitRepository;

    /**
     * 최근 본 카페 기록 조회
     */
    public List<GetCafeVisitRes> getCafeVisitLog(Long userId) {

        List<CafeVisit> CafeVisits = cafeVisitRepository.findCafeVisitsByUserId(userId);

        return CafeVisits.stream()
                .map(CafeVisit -> new GetCafeVisitRes(CafeVisit.getCafe()))
                .collect(Collectors.toList());
    }
}
