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
    private static final Long LAST_CURSOR = -1L;

    private List<GetReviewRes> reviewResponses;
    private Long totalElements;
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
