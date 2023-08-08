package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.user.entity.User;



@Schema(description = "리뷰 수정 Response : 등록한 리뷰 내용")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewPostOutDTO {
    @Schema(description = "카페명")
    private String cafeName;

    @Schema(description = "리뷰 작성자명(닉네임)")
    private String nickname;

    @Schema(description = "점수")
    private Integer score;

    @Schema(description = "내용")
    private String content;

    private String createdAt;

    public static ReviewPostOutDTO of(Review review) {
        Cafe cafe = review.getCafe();
        User user = review.getUser();

        return ReviewPostOutDTO.builder()
                .cafeName(cafe.getName())
                .nickname(user.getNickname())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
