package shop.cazait.domain.review.service;


import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_REVIEW;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_USER;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.review.dto.DelReviewRes;
import shop.cazait.domain.review.dto.PatchReviewReq;
import shop.cazait.domain.review.dto.PatchReviewRes;
import shop.cazait.domain.review.dto.PostReviewReq;
import shop.cazait.domain.review.dto.PostReviewRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.exception.ReviewException;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.domain.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class ReviewDaoService {
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    public PostReviewRes addReview(Long userId, Long cafeId, PostReviewReq postReviewReq) throws CafeException, UserException {
        User user = getUserReference(userId);
        Cafe cafe = getCafeReference(cafeId);

        Review newReview = postReviewReq.toEntity(cafe, user);
        reviewRepository.save(newReview);

        return PostReviewRes.of(newReview);
    }

    private Cafe getCafeReference(Long id) throws CafeException {
        try {
            Cafe cafe = cafeRepository.getReferenceById(id);
            return cafe;
        } catch (EntityNotFoundException ex) {  // 해당 엔티티가 존재하지 않을 시 EntityNotFoundException 발생
            throw new CafeException(NOT_EXIST_CAFE);
        }
    }

    private User getUserReference(Long id) throws UserException {
        try {
            User user = userRepository.getReferenceById(id);
            return user;
        } catch (EntityNotFoundException ex) {
            throw new UserException(NOT_EXIST_USER);
        }
    }

    public PatchReviewRes updateReview(PatchReviewReq patchReviewReq) throws ReviewException {
        Review review = reviewRepository.findById(patchReviewReq.getReviewId())
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        Review updatedReview = review.update(patchReviewReq);
        reviewRepository.save(updatedReview);   // 이미 트랜잭션이 동작 중일 때 저장하려고 한다면 OptimisticLockingFailureException 예외 발생

        return PatchReviewRes.of(updatedReview);
    }

    public DelReviewRes deleteReview(Long reviewId) throws ReviewException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(NOT_EXIST_REVIEW));

        reviewRepository.delete(review);

        return DelReviewRes.of(review);
    }
}
