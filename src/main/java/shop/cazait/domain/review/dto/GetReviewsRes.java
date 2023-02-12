package shop.cazait.domain.review.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.review.entity.Review;
import shop.cazait.global.pagination.ScrollPaginationCollection;



@Schema(description = "리뷰 전체 조회 Response : 해당 카페의 리뷰 한 페이지 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetReviewsRes {
    @Schema(description = "다음 스크롤이 존재하지 않을 경우 대입할 값")
    private static final Long LAST_CURSOR = -1L;

    @Schema(description = "전체 리뷰 Response : 해당 카페의 전체 리뷰 정보")
    private List<GetReviewRes> reviewResponses;

    @Schema(description = "한 스크롤에 나타낼 최대 데이터 갯수")
    private Long totalElements;

    @Schema(description = "다음에 조회할 리뷰의 ID (다음에 조회할 커서)")
    private Long nextCursor;

    public static GetReviewsRes of(ScrollPaginationCollection<Review> reviewScroll, Long totalElements) {
        if (reviewScroll.isLastScroll()) {
            return getNextScroll(reviewScroll.getCurrentScrollItems(), totalElements, LAST_CURSOR);
        }

        return getNextScroll(reviewScroll.getCurrentScrollItems(), totalElements,
                reviewScroll.getNextCursor().getId());
    }

    private static GetReviewsRes getNextScroll(List<Review> reviews, Long totalElements, Long nextCursor) {
        return GetReviewsRes.builder()
                .reviewResponses(reviews.stream()
                        .map(review -> GetReviewRes.of(review))
                        .collect(Collectors.toList()))
                .totalElements(totalElements)
                .nextCursor(nextCursor)
                .build();
    }
}
