package shop.cazait.domain.congestion.service;

import static shop.cazait.global.common.constant.Constant.NOT_EXIST_CONGESTION;
import static shop.cazait.global.error.status.ErrorStatus.INVALID_CONGESTION;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
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

    /**
     * 혼잡도 등록 및 수정
     */
    public PostCongestionRes addAndUpdateCongestion(Long cafeId, PostCongestionReq postCongestionReq) {

        Cafe findCafe = getCafe(cafeId);
        Congestion findCongestion = findCafe.getCongestion();
        Congestion newCongestion = null;

        CongestionStatus congestionStatus = getCongestionStatus(postCongestionReq.getCongestionStatus());

        if (findCongestion == NOT_EXIST_CONGESTION) {
            newCongestion = addCongestion(findCafe, congestionStatus);
        }

        if (findCongestion != NOT_EXIST_CONGESTION) {
            newCongestion = updateCongestion(findCongestion, congestionStatus);

        }

        return PostCongestionRes.of(newCongestion);

    }

    private Cafe getCafe(Long cafeId) throws CafeException {
        try {
            Cafe cafe = cafeRepository.findById(cafeId).get();
            return cafe;
        } catch (NoSuchElementException ex) {
            throw new CafeException(NOT_EXIST_CAFE);
        }
    }


    private CongestionStatus getCongestionStatus(String congestionStatus) {

        try {
            return CongestionStatus.valueOf(congestionStatus);
        } catch (IllegalArgumentException ex) {
            throw new CongestionException(INVALID_CONGESTION);
        }

    }

    private Congestion addCongestion(Cafe cafe, CongestionStatus congestionStatus) {

        Congestion addCongestion = PostCongestionReq.toEntity(cafe, congestionStatus);
        congestionRepository.save(addCongestion);
        cafe.changeCongestion(addCongestion);
        cafeRepository.save(cafe);

        return addCongestion;

    }

    private Congestion updateCongestion(Congestion findCongestion, CongestionStatus congestionStatus) {

        findCongestion.changeCongestionStatus(congestionStatus);
        Congestion updateCongestion = congestionRepository.save(findCongestion);

        return updateCongestion;

    }

}
