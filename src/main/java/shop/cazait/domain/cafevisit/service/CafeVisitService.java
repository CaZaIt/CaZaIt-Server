package shop.cazait.domain.cafevisit.service;

import static shop.cazait.domain.cafe.error.CafeErrorStatus.*;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.error.CafeErrorStatus;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafevisit.dto.GetCafeVisitRes;
import shop.cazait.domain.cafevisit.dto.PostCafeVisitRes;
import shop.cazait.domain.cafevisit.entity.CafeVisit;
import shop.cazait.domain.cafevisit.repository.CafeVisitRepository;
import shop.cazait.domain.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeVisitService {

    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final CafeVisitRepository cafeVisitRepository;

    /**
     * 최근 본 카페 기록 조회
     */
    @Transactional(readOnly = true)
    public List<GetCafeVisitRes> getVisitLog(Long userId) {

        List<CafeVisit> findVisitLogs = cafeVisitRepository.findCafeVisitsByUserId(userId).orElse(null);

        return GetCafeVisitRes.of(findVisitLogs);
    }

    /**
     * 최근 본 카페 등록
     */
    public PostCafeVisitRes addVisitLog(Long userId, Long cafeId) throws CafeException {

        User user = findUser(userId);
        Cafe cafe = findCafe(cafeId);

        CafeVisit addVisitLog = cafeVisitRepository.save(CafeVisit.builder()
                .user(user)
                .cafe(cafe)
                .build());

        return PostCafeVisitRes.of(addVisitLog);

    }

    private User findUser(Long userId) {
        try {
            return userRepository.findById(userId).get();
        } catch (NoSuchElementException exception) {
            throw new UserException();
        }
    }

    private Cafe findCafe(Long cafeId) throws CafeException {
        try {
            return cafeRepository.findById(cafeId).get();
        } catch (NoSuchElementException exception) {
            throw new CafeException(NON_EXIST_CAFE);
        }
    }



}
