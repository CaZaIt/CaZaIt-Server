package shop.cazait.domain.review.dto;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.review.repository.ReviewRepository;
import shop.cazait.domain.user.entity.User;
import shop.cazait.domain.user.repository.UserRepository;



@Getter
public class PostReviewReq {
    private long cafeId;
    private long userId;
    private int score;
    private String content;


    @Builder
    public PostReviewReq(long cafeId, long userId, int score, String content) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.score = score;
        this.content = content;
    }

    @Builder
    public Review toEntity(Cafe cafe, User user) {
        return Review.builder()
                .cafe(cafe)
                .user(user)
                .score(score)
                .content(content)
                .build();
    }
}
