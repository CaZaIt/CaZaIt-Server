package shop.cazait.domain.checklog.api;

import static shop.cazait.global.error.status.SuccessStatus.*;

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
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;

@Api(tags = "방문 기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklogs")
public class CheckLogApiController {

    private final CheckLogService cafeVisitService;


    @ApiOperation(value = "조회 기록 가져오기", notes = "사용자의 ID를 통해 조회 기록을 가져온다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID")
    @GetMapping("/user/{userId}")
    public SuccessResponse<List<GetCheckLogRes>> getVisitLog(@PathVariable(name = "userId") Long userId) {

        List<GetCheckLogRes> result = cafeVisitService.getVisitLog(userId);
        SuccessStatus resultStatus = SUCCESS;

        if(result == null) {
            resultStatus = NO_CONTENT_SUCCESS;
        }

        return new SuccessResponse<>(resultStatus, result);

    }

}
