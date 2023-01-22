package shop.cazait.domain.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.user.entity.User;



@ApiModel(value = "리뷰 등록 Request DTO", description = "리뷰 등록을 위해 필요한 정보")
@Getter
public class PostReviewReq {
    @ApiModelProperty(value = "카페 ID", example = "1")
    @NotNull(message = "존재하지 않는 카페입니다.")
    private Long cafeId;

    @ApiModelProperty(value = "유저 ID", example = "1")
    @NotNull(message = "존재하지 않는 유저입니다.")
    private Long userId;

    @ApiModelProperty(value = "점수", example = "5")
    @NotNull(message = "점수를 입력해주세요.")
    @Min(value = 1, message = "점수는 1점 이상이여야합니다.")
    @Max(value = 5, message = "점수는 5점 이하여야합니다.")
    private Integer score;

    @ApiModelProperty(value = "리뷰 내용", example = "얌2~")
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
