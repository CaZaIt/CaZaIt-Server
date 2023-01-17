package shop.cazait.domain.congestion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.congestion.dto.PostCongestionReq;
import shop.cazait.domain.congestion.dto.PostCongestionRes;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
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
            newCongestion = updateCongestion(findCongestion, postCongestionReq);
        }

        return PostCongestionRes.of(newCongestion);

    }

    public Congestion addCongestion(Long cafeId, PostCongestionReq request) {

        CongestionStatus congestionStatus = CongestionStatus.valueOf(request.getCongestionStatus());
        Congestion congestion = PostCongestionReq.toEntity(cafeRepository.findById(cafeId), congestionStatus);
        Congestion addCongestion =  congestionRepository.save(congestion);

        return addCongestion;

    }

    public Congestion updateCongestion(Congestion findCongestion, PostCongestionReq request) {

        CongestionStatus congestionStatus = CongestionStatus.valueOf(request.getCongestionStatus());
        findCongestion.changeCongestionStatus(congestionStatus);
        Congestion updateCongestion = congestionRepository.save(findCongestion);

        return updateCongestion;

    }

}
