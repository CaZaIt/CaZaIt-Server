package shop.cazait.domain.review.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.review.dto.GetReviewsRes;
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

    @ResponseBody
    @ApiOperation(value = "리뷰 전체 조회", notes = "카페 ID를 받아 해당 카페의 리뷰 목록 및 평점을 반환")
    @ApiImplicitParam(
            name = "cafeId",
            value = "카페 ID"
    )
    @GetMapping("/{cafeId}")
    public BaseResponse<GetReviewsRes> getReviews(@PathVariable long cafeId) {
        GetReviewsRes getReviewsRes = reviewProvideService.getReviews(cafeId);

        return new BaseResponse<>(getReviewsRes);
    }
}
