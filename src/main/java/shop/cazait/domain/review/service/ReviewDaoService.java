package shop.cazait.domain.review.service;


import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.review.dto.DelReviewRes;
import shop.cazait.domain.review.dto.PatchReviewReq;
import shop.cazait.domain.review.dto.PatchReviewRes;
import shop.cazait.domain.review.dto.PostReviewReq;
import shop.cazait.domain.review.dto.PostReviewRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;



@Service
@RequiredArgsConstructor
@Transactional
public class ReviewDaoService {
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    public PostReviewRes addReview(PostReviewReq postReviewReq) throws EntityNotFoundException {
        Cafe cafe = getCafeReference(postReviewReq.getCafeId());
        User user = getUserReference(postReviewReq.getUserId());

        Review newReview = postReviewReq.toEntity(cafe, user);
        reviewRepository.save(newReview);

        return PostReviewRes.of(newReview);
    }

    private Cafe getCafeReference(Long id) {
        try {
            Cafe cafe = cafeRepository.getReferenceById(id);

            return cafe;
        } catch (EntityNotFoundException ex) {  // 해당 엔티티가 존재하지 않을 시 EntityNotFoundException 발생
            throw ex;
        }
    }

    private User getUserReference(Long id) {
        try {
            User user = userRepository.getReferenceById(id);

            return user;
        } catch (EntityNotFoundException ex) {
            throw ex;
        }
    }

    public PatchReviewRes updateReview(PatchReviewReq patchReviewReq) throws EntityNotFoundException {
        Review review = reviewRepository.findById(patchReviewReq.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException());

        Review updatedReview = review.update(patchReviewReq);
        reviewRepository.save(updatedReview);   // 이미 트랜잭션이 동작 중일 때 저장하려고 한다면 OptimisticLockingFailureException 예외 발생

        return PatchReviewRes.of(updatedReview);
    }

    public DelReviewRes deleteReview(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException());

        reviewRepository.delete(review);

        return DelReviewRes.of(review);
    }
}
