package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;



@Schema(description = "리뷰 하나 조회 Response : 해당 리뷰의 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewGetOutDTO {
    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "카페 ID")
    private Long cafeId;

    @Schema(description = "점수")
    private Integer score;

    @Schema(description = "내용")
    private String content;

    public static ReviewGetOutDTO of(Review review) {
        return ReviewGetOutDTO.builder()
                .userId(review.getUser().getId())
                .cafeId(review.getCafe().getId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
