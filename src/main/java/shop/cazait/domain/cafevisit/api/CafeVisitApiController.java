package shop.cazait.domain.cafevisit.api;

import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafevisit.dto.GetCafeVisitRes;
import shop.cazait.domain.cafevisit.service.CafeVisitService;
import shop.cazait.global.common.response.BaseResponse;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe_visit")
public class CafeVisitApiController {

    private final CafeVisitService cafeVisitService;

    /**
     * 최근 본 카페 기록 조회
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetCafeVisitRes>> getCafeVisitLog(@PathVariable("userId") Long userId) {

        List<GetCafeVisitRes> cafeVisitRes = cafeVisitService.getCafeVisitLog(userId);

        return new BaseResponse<>(cafeVisitRes);
    }

}
