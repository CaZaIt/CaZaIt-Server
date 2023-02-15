package shop.cazait.domain.checklog.api;

import static shop.cazait.global.error.status.SuccessStatus.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "방문 기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklogs")
public class CheckLogApiController {

    private final CheckLogService cafeVisitService;


    @Operation(summary = "조회 기록 가져오기", description = "유저 ID를 통해 조회 기록을 가져온다.")
    @Parameter(name = "userId", description = "조회 기록을 가져올 유저에 대한 ID")
    @GetMapping("/user/{userId}")
    public SuccessResponse<List<GetCheckLogRes>> getVisitLog(@PathVariable Long userId) {

        List<GetCheckLogRes> result = cafeVisitService.getVisitLog(userId);
        SuccessStatus resultStatus = SUCCESS;

        if(result == null) {
            resultStatus = NO_CONTENT_SUCCESS;
        }

        return new SuccessResponse<>(resultStatus, result);

    }

}
