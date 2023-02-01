package shop.cazait.domain.checklog.service;

import static shop.cazait.global.error.status.ErrorStatus.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.checklog.dto.GetCheckLogRes;
import shop.cazait.domain.checklog.entity.CheckLog;
import shop.cazait.domain.checklog.repository.CheckLogRepository;
import shop.cazait.domain.favorites.entity.Favorites;
import shop.cazait.domain.favorites.repository.FavoritesRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckLogService {

    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final CheckLogRepository checkLogRepository;
    private final FavoritesRepository favoritesRepository;

    /**
     * 최근 본 카페 기록 조회
     */
    @Transactional(readOnly = true)
    public List<GetCheckLogRes> getVisitLog(Long userId) {

        List<CheckLog> visitLogs = checkLogRepository.findVisitLogByUserId(userId).orElse(null);
        List<Favorites> favorites = favoritesRepository.findAllByUserId(userId).get();

        List<GetCheckLogRes> getCheckLogRes = new ArrayList<>();
        for (CheckLog log : visitLogs) {
            boolean isFavorites = checkFavorites(log, favorites);
            getCheckLogRes.add(GetCheckLogRes.of(log, isFavorites));
        }

        return getCheckLogRes;

    }

    private boolean checkFavorites(CheckLog logs, List<Favorites> favorites) {
        return favorites.stream()
                .anyMatch(f -> f.getCafe().equals(logs.getCafe()));
    }

    /**
     * 최근 본 카페 등록
     */
    public String addVisitLog(Long userId, Long cafeId) throws CafeException, UserException {

        User user = getUser(userId);
        Cafe cafe = getCafe(cafeId);

        CheckLog visitLog = checkLogRepository.save(CheckLog.builder()
                .user(user)
                .cafe(cafe)
                .build());

        return "방문 기록 등록 완료.";
    }

    private User getUser(Long userId) throws UserException {
        try {
            return userRepository.getReferenceById(userId);
        } catch (EntityNotFoundException exception) {
            throw new UserException(NOT_EXIST_USER);
        }
    }

    private Cafe getCafe(Long cafeId) throws CafeException {
        try {
            return cafeRepository.getReferenceById(cafeId);
        } catch (EntityNotFoundException exception) {
            throw new CafeException(NOT_EXIST_CAFE);
        }
    }



}
