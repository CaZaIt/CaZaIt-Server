package shop.cazait.domain.review.service;


import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.review.dto.PostReviewReq;
import shop.cazait.domain.review.dto.PostReviewRes;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class ReviewDaoService {
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    @Transactional
    public PostReviewRes addReview(PostReviewReq postReviewReq) {
        Cafe cafe = cafeRepository.getReferenceById(postReviewReq.getCafeId());
        User user = userRepository.getReferenceById(postReviewReq.getUserId());

        Review review = postReviewReq.toEntity(cafe, user);

        reviewRepository.save(review);

        return PostReviewRes.of(review);
    }
}
