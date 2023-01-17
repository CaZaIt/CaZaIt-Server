package shop.cazait.domain.cafe.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.global.common.response.BaseResponse;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;

@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @ResponseBody
    @GetMapping("/all")
    public BaseResponse<List<GetCafeRes>> getCafeAll() {
        List<GetCafeRes> cafeResList = this.cafeService.getCafeList(BaseStatus.ACTIVE);
        return new BaseResponse<>(cafeResList);
    }

}