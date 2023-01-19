package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.user.entity.User;



@ApiModel(value = "리뷰 등록 Request DTO", description = "리뷰 등록을 위해 필요한 정보")
@Getter
public class PostReviewReq {
    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "유저 ID")
    private Long userId;

    @ApiModelProperty(value = "점수")
    private Integer score;

    @ApiModelProperty(value = "내용")
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
