package shop.cazait.domain.review.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.review.dto.DelReviewRes;
import shop.cazait.domain.review.dto.GetReviewRes;
import shop.cazait.domain.review.dto.GetReviewsRes;
import shop.cazait.domain.review.dto.PatchReviewReq;
import shop.cazait.domain.review.dto.PatchReviewRes;
import shop.cazait.domain.review.dto.PostReviewReq;
import shop.cazait.domain.review.dto.PostReviewRes;
import shop.cazait.domain.review.service.ReviewDaoService;
import shop.cazait.domain.review.service.ReviewProvideService;
import shop.cazait.global.common.response.BaseResponse;



@RestController
@RequiredArgsConstructor
@Api(tags = "리뷰 API")
@RequestMapping("/api/reviews")
public class ReviewApiController {
    private final ReviewDaoService reviewDaoService;
    private final ReviewProvideService reviewProvideService;

    @ApiOperation(value = "리뷰 전체 조회", notes = "카페 ID를 받아 해당 카페의 리뷰 목록 및 평점을 반환")
    @ApiImplicitParam(
            name = "cafeId",
            value = "카페 ID"
    )
    @GetMapping("/{cafeId}")
    public BaseResponse<GetReviewsRes> getReviews(@PathVariable Long cafeId) {
        GetReviewsRes getReviewsRes = reviewProvideService.getReviews(cafeId);

        return new BaseResponse<>(getReviewsRes);
    }

    @GetMapping("/{reviewId}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable Long reviewId) {
        GetReviewRes getReviewRes = reviewProvideService.getReview(reviewId);

        return new BaseResponse<>(getReviewRes);
    }

    @PostMapping("/{cafeId}")
    public BaseResponse<PostReviewRes> addReview(PostReviewReq postReviewReq) {
        PostReviewRes postReviewRes = reviewDaoService.addReview(postReviewReq);

        return new BaseResponse<>(postReviewRes);
    }

    @PatchMapping("/{reviewId}")
    public BaseResponse<PatchReviewRes> updateReview(PatchReviewReq patchReviewReq) {
        PatchReviewRes patchReviewRes = reviewDaoService.updateReview(patchReviewReq);

        return new BaseResponse<>(patchReviewRes);
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<DelReviewRes> deleteReview(@PathVariable Long reviewId) {
        DelReviewRes delReviewRes = reviewDaoService.deleteReview(reviewId);

        return new BaseResponse<>(delReviewRes);
    }
}
