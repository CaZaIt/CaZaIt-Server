package shop.cazait.domain.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.review.service.ReviewDaoService;
import shop.cazait.domain.review.service.ReviewProvideService;



@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewDaoService reviewDaoService;
    private final ReviewProvideService reviewProvideService;
}
