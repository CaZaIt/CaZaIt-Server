package shop.cazait.domain.review.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import shop.cazait.domain.review.entity.Review;



@Schema(description = "리뷰 전체 조회 Response : 해당 카페의 리뷰 한 페이지 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewsGetOutDTO {
    @Schema(description = "전체 리뷰 Response : 해당 카페의 전체 리뷰 정보")
    private List<ReviewGetOutDTO> reviewResponses;

    @Schema(description = "한 페이지의 리뷰 개수")
    private Integer totalElements;

    @Schema(description = "마지막 페이지 여부")
    private Boolean isLast;

    public static ReviewsGetOutDTO of(Slice<Review> reviews) {
        List<ReviewGetOutDTO> reviewGetOutDTOs = reviews.stream()
                .map(ReviewGetOutDTO::of)
                .collect(Collectors.toUnmodifiableList());

        return ReviewsGetOutDTO.builder()
                .isLast(reviews.isLast())
                .totalElements(reviews.getNumberOfElements())
                .reviewResponses(reviewGetOutDTOs)
                .build();
    }
}
