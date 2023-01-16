package shop.cazait.domain.cafevisit.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "최근 본 카페 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe_visits")
public class CafeVisitApiController {

    private final CafeVisitService cafeVisitService;

    /**
     * 최근 본 카페 기록 조회
     */
    @ApiOperation(
            value = "최근 본 카페 기록 조회", notes = "사용자의 ID를 통해 최근 본 카페 목록을 조회"
    )
    @ApiImplicitParam(
            name = "userId", value = "사용자 ID"
    )
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetCafeVisitRes>> getCafeVisitLog(@PathVariable(name = "userId") Long userId) {

        List<GetCafeVisitRes> cafeVisitRes = cafeVisitService.getCafeVisitLog(userId);

        return new BaseResponse<>(cafeVisitRes);
    }

}
