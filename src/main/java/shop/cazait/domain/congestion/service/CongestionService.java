package shop.cazait.domain.congestion.service;

import static shop.cazait.domain.congestion.exception.CongestionErrorStatus.INVALID_CONGESTION_STATUS;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.congestion.dto.PostCongestionReq;
import shop.cazait.domain.congestion.dto.PostCongestionRes;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.domain.congestion.exception.CongestionException;
import shop.cazait.domain.congestion.repository.CongestionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CongestionService {

    private final CafeRepository cafeRepository;
    private final CongestionRepository congestionRepository;

    public PostCongestionRes addAndUpdateCongestion(Long cafeId, PostCongestionReq postCongestionReq) {

        CongestionStatus congestionStatus;
        Congestion newCongestion = null;
        Congestion findCongestion = congestionRepository.findByCafeId(cafeId).orElse(null);

        try {
            congestionStatus = CongestionStatus.valueOf(postCongestionReq.getCongestionStatus());
        } catch (IllegalArgumentException ex) {
            throw new CongestionException(INVALID_CONGESTION_STATUS);
        }


        if (findCongestion == null) {
            newCongestion = addCongestion(cafeId, congestionStatus);
        }

        if (findCongestion != null) {
            newCongestion = updateCongestion(findCongestion, congestionStatus);
        }

        return PostCongestionRes.of(newCongestion);

    }

    private Congestion addCongestion(Long cafeId, CongestionStatus congestionStatus) {

        Congestion congestion = PostCongestionReq.toEntity(cafeRepository.findById(cafeId), congestionStatus);
        Congestion addCongestion =  congestionRepository.save(congestion);

        return addCongestion;

    }

    private Congestion updateCongestion(Congestion findCongestion, CongestionStatus congestionStatus) {

        findCongestion.changeCongestionStatus(congestionStatus);
        Congestion updateCongestion = congestionRepository.save(findCongestion);

        return updateCongestion;

    }

}
