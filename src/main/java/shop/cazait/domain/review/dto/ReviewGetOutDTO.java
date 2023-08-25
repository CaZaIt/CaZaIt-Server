package shop.cazait.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.domain.user.entity.User;



@Schema(description = "리뷰 하나 조회 Response : 해당 리뷰의 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewGetOutDTO {
    @Schema(description = "유저 ID")
    private UUID userId;

    @Schema(description = "카페명")
    private String cafeName;

    @Schema(description = "리뷰 작성자명(닉네임)")
    private String nickname;

    @Schema(description = "리뷰 점수")
    private Integer score;

    @Schema(description = "리뷰 내용")
    private String content;

    public static ReviewGetOutDTO of(Review review) {
        User user = review.getUser();
        Cafe cafe = review.getCafe();

        return ReviewGetOutDTO.builder()
                .userId(user.getId())
                .cafeName(cafe.getName())
                .nickname(user.getNickname())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
