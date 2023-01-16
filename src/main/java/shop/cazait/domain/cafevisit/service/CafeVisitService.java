package shop.cazait.domain.cafevisit.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafevisit.dto.GetCafeVisitRes;
import shop.cazait.domain.cafevisit.dto.PostCafeVisitRes;
import shop.cazait.domain.cafevisit.entity.CafeVisit;
import shop.cazait.domain.cafevisit.repository.CafeVisitRepository;
import shop.cazait.domain.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeVisitService {

    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
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

    /**
     * 최근 본 카페 등록
     */
    public PostCafeVisitRes addCafeVisit(Long userId, Long cafeId) {

        User user = userRepository.findById(userId);
        Cafe cafe = cafeRepository.findById(cafeId);

        CafeVisit addCafeVisit = cafeVisitRepository.save(CafeVisit.builder()
                .user(user)
                .cafe(cafe)
                .build());

        return PostCafeVisitRes.builder()
                .cafeVisitId(addCafeVisit.getId())
                .userName(addCafeVisit.getUser().getNickname())
                .cafeName(addCafeVisit.getCafe().getName())
                .build();

    }

}
