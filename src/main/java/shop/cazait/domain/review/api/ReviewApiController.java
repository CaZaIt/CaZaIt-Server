package shop.cazait.domain.review.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_REVIEW;
import static shop.cazait.global.error.status.SuccessStatus.NO_CONTENT_SUCCESS;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.review.dto.ReviewDeleteOutDTO;
import shop.cazait.domain.review.dto.ReviewGetOutDTO;
import shop.cazait.domain.review.dto.ReviewPostInDTO;
import shop.cazait.domain.review.dto.ReviewPostOutDTO;
import shop.cazait.domain.review.dto.ReviewUpdateInDTO;
import shop.cazait.domain.review.dto.ReviewUpdateOutDTO;
import shop.cazait.domain.review.dto.ReviewsGetOutDTO;
import shop.cazait.domain.review.exception.ReviewException;
import shop.cazait.domain.review.service.ReviewDaoService;
import shop.cazait.domain.review.service.ReviewProvideService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;



@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API")
@RequestMapping("/api/reviews")
public class ReviewApiController {
    private final ReviewDaoService reviewDaoService;
    private final ReviewProvideService reviewProvideService;

    @NoAuth
    @GetMapping("/{cafeId}/all")
    @Operation(summary = "리뷰 전체 조회", description = "카페 ID를 받아 해당 카페의 리뷰 목록 반환 (성공: 200, 리뷰 없음: 204, 존재하지 않는 카페: 404)")
    @Parameters({
            @Parameter(name = "cafeId", description = "카페 ID"),
            @Parameter(name = "sortBy", description = "정렬 기준(newest, oldest)", example = "newest"),
            @Parameter(name = "score", description = "리뷰 점수"),
            @Parameter(name = "index", description = "페이지 번호 (0부터 시작)"),
            @Parameter(name = "nums", description = "한 페이지에 보여줄 리뷰의 갯수")
    })
    public SuccessResponse<ReviewsGetOutDTO> getReviews(@PathVariable UUID cafeId,
                                                        @RequestParam(value = "sortBy", defaultValue = "newest") String sortBy,
                                                        @RequestParam(value = "index", defaultValue = "0") Integer index,
                                                        @RequestParam(value = "nums") Integer nums,
                                                        @RequestParam(value = "score", required = false) Integer score)
            throws CafeException, ReviewException {
        ReviewsGetOutDTO getReviewsRes = reviewProvideService.getReviews(cafeId, index, nums, sortBy, score);
        SuccessStatus resultStatus = SUCCESS;

        if (getReviewsRes == null) {
            resultStatus = NO_CONTENT_SUCCESS;
        }

        return new SuccessResponse<>(resultStatus, getReviewsRes);
    }

    @NoAuth
    @GetMapping("/{cafeId}/score")
    @Operation(summary = "카페 평점 조회", description = "카페 ID를 받아 해당 카페의 평점 반환 (성공: 200, 존재하지 않는 카페: 404)")
    @Parameter(name = "cafeId", description = "카페 ID")
    public SuccessResponse<Double> getAverageScore(@PathVariable UUID cafeId) throws CafeException {
        Double averageScore = reviewProvideService.getAverageScore(cafeId);

        return new SuccessResponse<>(SUCCESS, averageScore);
    }

    @NoAuth
    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 하나 조회", description = "리뷰 ID를 받아 해당 리뷰 조회 (성공: 200, 존재하지 않는 리뷰: 404)")
    @Parameter(name = "reviewId", description = "리뷰 ID")
    public SuccessResponse<ReviewGetOutDTO> getReview(@PathVariable UUID reviewId) throws ReviewException {
        ReviewGetOutDTO getReviewRes = reviewProvideService.getReviewDetail(reviewId);

        return new SuccessResponse<>(SUCCESS, getReviewRes);
    }

    @PostMapping("/user/{userId}/cafe/{cafeId}")
    @Operation(summary = "리뷰 작성", description = "유저와 카페 ID를 받아 해당 카페의 리뷰 작성 (성공: 200, 존재하지 않는 유저 및 카페: 404)")
    @Parameters({
            @Parameter(name = "userId", description = "유저 ID"),
            @Parameter(name = "cafeId", description = "카페 ID")
    })
    public SuccessResponse<ReviewPostOutDTO> addReview(@PathVariable UUID userId,
                                                       @PathVariable UUID cafeId,
                                                       @RequestBody @Valid ReviewPostInDTO postReviewReq)
            throws UserException, CafeException, ReviewException {
        ReviewPostOutDTO postReviewRes = reviewDaoService.addReview(userId, cafeId, postReviewReq);
        return new SuccessResponse<>(CREATE_REVIEW, postReviewRes);
    }

    @PatchMapping("/edit/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 ID를 받아 해당 리뷰 점수 및 내용 수정 (성공: 200, 존재하지 않는 리뷰: 404)")
    public SuccessResponse<ReviewUpdateOutDTO> updateReview(@PathVariable UUID reviewId,
                                                            @RequestBody @Valid ReviewUpdateInDTO patchReviewReq)
            throws ReviewException {
        ReviewUpdateOutDTO patchReviewRes = reviewDaoService.updateReview(reviewId, patchReviewReq);
        return new SuccessResponse<>(SUCCESS, patchReviewRes);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 받아 해당 리뷰 삭제 (성공: 200, 존재하지 않는 리뷰: 404)")
    public SuccessResponse<ReviewDeleteOutDTO> deleteReview(@PathVariable UUID reviewId) throws ReviewException {
        ReviewDeleteOutDTO delReviewRes = reviewDaoService.deleteReview(reviewId);
        return new SuccessResponse<>(SUCCESS, delReviewRes);
    }
}
