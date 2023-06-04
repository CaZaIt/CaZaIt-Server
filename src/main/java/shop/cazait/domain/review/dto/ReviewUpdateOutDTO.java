package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;



@Schema(description = "리뷰 수정 Response : 수정한 리뷰 내용")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewUpdateOutDTO {
    @Schema(description = "리뷰 ID")
    private Long reviewId;

    @Schema(description = "점수")
    private Integer score;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "수정된 일시")
    private String updatedAt;

    public static ReviewUpdateOutDTO of(Review review) {
        return ReviewUpdateOutDTO.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
