package shop.cazait.domain.congestion.service;

import static shop.cazait.domain.congestion.entity.FirstAccessStatus.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.congestion.dto.PostCongestionReq;
import shop.cazait.domain.congestion.dto.PostCongestionRes;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.repository.CongestionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CongestionService {

    private final CafeRepository cafeRepository;
    private final CongestionRepository congestionRepository;

    public PostCongestionRes addAndUpdateCongestion(Long cafeId, PostCongestionReq postCongestionReq) {

        Congestion findCongestion = congestionRepository.findByCafeId(cafeId).orElse(null);
        Congestion newCongestion = null;

        if (findCongestion == null) {
            newCongestion = addCongestion(cafeId, postCongestionReq);
        }

        if (findCongestion != null) {
            newCongestion = updateCongestion(findCongestion, cafeId, postCongestionReq);
        }

        return PostCongestionRes.builder()
                .congestionId(newCongestion.getId())
                .cafeId(newCongestion.getCafe().getId())
                .congestionStatus(newCongestion.getCongestionStatus())
                .build();

    }

    public Congestion addCongestion(Long cafeId, PostCongestionReq postCongestionReq) {

        Congestion congestion = Congestion.builder()
                .cafe(cafeRepository.findById(cafeId))
                .congestionStatus(postCongestionReq.getCongestionStatus())
                .build();

        Congestion addCongestion =  congestionRepository.save(congestion);

        return addCongestion;

    }

    public Congestion updateCongestion(Congestion findCongestion, Long cafeId, PostCongestionReq postCongestionReq) {

        findCongestion.changeCongestionStatus(postCongestionReq.getCongestionStatus());
        Congestion updateCongestion = congestionRepository.save(findCongestion);

        return updateCongestion;

    }

}
