package shop.cazait.domain.review.api;

import static shop.cazait.global.error.status.SuccessStatus.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
import shop.cazait.domain.review.dto.DelReviewRes;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.PatchReviewReq;
import shop.cazait.domain.review.dto.PatchReviewRes;
import shop.cazait.domain.review.dto.PostReviewReq;
import shop.cazait.domain.review.dto.PostReviewRes;
import shop.cazait.domain.review.service.ReviewDaoService;
import shop.cazait.domain.review.service.ReviewProvideService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;


@RestController
@RequiredArgsConstructor
@Api(tags = "리뷰 API")
@RequestMapping("/api/reviews")
public class ReviewApiController {
    private final ReviewDaoService reviewDaoService;
    private final ReviewProvideService reviewProvideService;

    @ApiOperation(value = "리뷰 전체 조회", notes = "카페 ID를 받아 해당 카페의 리뷰 목록 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID"),
            @ApiImplicitParam(name = "sortBy", value = "정렬 기준(newest, oldest, popularity)", defaultValue = "newest"),
            @ApiImplicitParam(name = "score", value = "리뷰 점수")
    })
    @GetMapping("/{cafeId}/all")
    public SuccessResponse<List<GetReviewRes>> getReviews(@PathVariable Long cafeId,
                                                          @RequestParam(value = "sortBy", defaultValue = "newest") String sortBy,
                                                          @RequestParam(value = "score", required = false) Integer score) {
        List<GetReviewRes> getReviewsRes = reviewProvideService.getReviews(cafeId, sortBy, score);
        SuccessStatus resultStatus = SUCCESS;

        if (getReviewsRes == null)
            resultStatus = NO_CONTENT_SUCCESS;

        return new SuccessResponse<>(resultStatus, getReviewsRes);
    }

    @ApiOperation(value = "카페 평점 조회", notes = "카페 ID를 받아 해당 카페의 평점 반환")
    @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    @GetMapping("/{cafeId}/score")
    public SuccessResponse<Double> getAverageScore(@PathVariable Long cafeId) {
        Double averageScore = reviewProvideService.getAverageScore(cafeId);

        return new SuccessResponse<>(SUCCESS, averageScore);
    }


    @ApiOperation(value = "리뷰 하나 조회", notes = "리뷰 ID를 받아 해당 리뷰 조회")
    @ApiImplicitParam(name = "reviewId", value = "리뷰 ID")
    @GetMapping("/{reviewId}")
    public SuccessResponse<GetReviewRes> getReview(@PathVariable Long reviewId) {
        GetReviewRes getReviewRes = reviewProvideService.getReviewDetail(reviewId);

        return new SuccessResponse<>(SUCCESS, getReviewRes);
    }

    @ApiOperation(value = "리뷰 작성", notes = "유저와 카페 ID를 받아 해당 카페의 리뷰 작성")
    @PostMapping("/user/{userId}/cafe/{cafeId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID"),
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    public SuccessResponse<PostReviewRes> addReview(@PathVariable Long userId,
                                                    @PathVariable Long cafeId,
                                                    @RequestBody @Valid PostReviewReq postReviewReq)
            throws UserException {
        PostReviewRes postReviewRes = reviewDaoService.addReview(userId, cafeId, postReviewReq);
        return new SuccessResponse<>(CREATE_REVIEW, postReviewRes);
    }

    @ApiOperation(value = "리뷰 수정", notes = "리뷰 ID를 받아 해당 리뷰 점수 및 내용 수정")
    @PatchMapping("/edit/{reviewId}")
    public SuccessResponse<PatchReviewRes> updateReview(@PathVariable Long reviewId,
                                                        @RequestBody @Valid PatchReviewReq patchReviewReq) {
        PatchReviewRes patchReviewRes = reviewDaoService.updateReview(reviewId, patchReviewReq);
        return new SuccessResponse<>(SUCCESS, patchReviewRes);
    }

    @ApiOperation(value = "리뷰 삭제", notes = "리뷰 ID를 받아 해당 리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public SuccessResponse<DelReviewRes> deleteReview(@PathVariable Long reviewId) {
        DelReviewRes delReviewRes = reviewDaoService.deleteReview(reviewId);
        return new SuccessResponse<>(SUCCESS, delReviewRes);
    }
}
