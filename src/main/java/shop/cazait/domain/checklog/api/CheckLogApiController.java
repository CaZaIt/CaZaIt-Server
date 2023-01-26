package shop.cazait.domain.checklog.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.checklog.dto.GetCheckLogRes;
import shop.cazait.domain.checklog.service.CheckLogService;
import shop.cazait.global.common.response.SuccessResponse;

@Api(tags = "방문 기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklogs")
public class CheckLogApiController {

    private final CheckLogService cafeVisitService;


    @ApiOperation(value = "조회 기록 가져오기", notes = "사용자의 ID를 통해 조회 기록을 가져온다.")
    @ApiImplicitParam(
            name = "userId", value = "사용자 ID"
    )
    @GetMapping("/user/{userId}")
    public SuccessResponse<List<GetCheckLogRes>> getVisitLog(@PathVariable(name = "userId") Long userId) {

        // User ID가 null 인지 확인
        // JWT에서 받아온 User ID와 같은지 확인

        List<GetCheckLogRes> result = cafeVisitService.getVisitLog(userId);
        return new SuccessResponse<>(result);

    }

}
