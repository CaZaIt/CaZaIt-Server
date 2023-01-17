package shop.cazait.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.user.entity.User;



@Getter
public class PostReviewReq {
    private Long cafeId;
    private Long userId;
    private Integer score;
    private String content;


    @Builder
    public PostReviewReq(Long cafeId, Long userId, Integer score, String content) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.score = score;
        this.content = content;
    }

    public Review toEntity(Cafe cafe, User user) {
        return Review.builder()
                .cafe(cafe)
                .user(user)
                .score(score)
                .content(content)
                .build();
    }
}
