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
    @Schema(description = "카페명")
    private String cafeName;

    @Schema(description = "리뷰 작성자명(닉네임)")
    private String nickname;

    @Schema(description = "리뷰 점수")
    private Integer score;

    @Schema(description = "리뷰 내용")
    private String content;

    public static ReviewGetOutDTO of(Review review) {
        return ReviewGetOutDTO.builder()
                .cafeName(review.getCafe().getName())
                .nickname(review.getUser().getNickname())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
