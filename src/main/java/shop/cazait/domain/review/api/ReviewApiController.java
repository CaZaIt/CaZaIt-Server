package shop.cazait.domain.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.review.service.ReviewDaoService;
import shop.cazait.domain.review.service.ReviewProvideService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/app/cafe")
public class ReviewApiController {
    private final ReviewDaoService reviewDaoService;
    private final ReviewProvideService reviewProvideService;

    @ResponseBody
    @GetMapping("/{cafeId}/reviews")
    public BaseResponse<GetReviewsRes> getReviews(@PathVariable long cafeId) {
        GetReviewsRes getReviewsRes = reviewProvideService.getReviews(cafeId);

        return new BaseResponse<>(getReviewsRes);
    }
}
