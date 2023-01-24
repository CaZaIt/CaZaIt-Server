package shop.cazait.domain.checklog.service;

import static shop.cazait.domain.cafe.error.CafeErrorStatus.*;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.checklog.dto.GetCheckLogRes;
import shop.cazait.domain.checklog.dto.PostCheckLogRes;
import shop.cazait.domain.checklog.entity.CheckLog;
import shop.cazait.domain.checklog.repository.CheckLogRepository;
import shop.cazait.domain.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckLogService {

    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final CheckLogRepository checkLogRepository;

    /**
     * 최근 본 카페 기록 조회
     */
    @Transactional(readOnly = true)
    public List<GetCheckLogRes> getVisitLog(Long userId) {

        List<CheckLog> visitLogs = checkLogRepository.findVisitLogByUserId(userId).orElse(null);

        return GetCheckLogRes.of(visitLogs);
    }

    /**
     * 최근 본 카페 등록
     */
    public PostCheckLogRes addVisitLog(Long userId, Long cafeId) throws CafeException {

        User user = getUser(userId);
        Cafe cafe = getCafe(cafeId);

        CheckLog visitLog = checkLogRepository.save(CheckLog.builder()
                .user(user)
                .cafe(cafe)
                .build());

        return PostCheckLogRes.of(visitLog);

    }

    private User getUser(Long userId) {
        try {
            return userRepository.getReferenceById(userId);
        } catch (EntityNotFoundException exception) {
            throw new UserException();
        }
    }

    private Cafe getCafe(Long cafeId) throws CafeException {
        try {
            return cafeRepository.getReferenceById(cafeId);
        } catch (EntityNotFoundException exception) {
            throw new CafeException(NON_EXIST_CAFE);
        }
    }



}
