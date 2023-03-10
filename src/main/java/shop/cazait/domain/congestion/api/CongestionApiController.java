package shop.cazait.domain.congestion.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.congestion.dto.PostCongestionReq;
import shop.cazait.domain.congestion.dto.PostCongestionRes;
import shop.cazait.domain.congestion.exception.CongestionException;
import shop.cazait.domain.congestion.service.CongestionService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "혼잡도 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/congestions")
public class CongestionApiController {

    private final CongestionService congestionService;

    @Operation(summary = "혼잡도 등록 및 수정", description = "마스터 ID, 카페 ID, 혼잡도 상태를 받아 혼잡도를 수정 또는 등록한다.")
    @Parameters({
            @Parameter(name = "masterId", description = "카페에 대한 권한이 있는 마스터 ID"),
            @Parameter(name = "cafeId", description = "혼잡도를 등록 또는 수정할 카페 ID")
    })
    @PostMapping("/master/{masterId}/cafe/{cafeId}")
    public SuccessResponse<PostCongestionRes> addCongestion(@PathVariable Long masterId,
                                                            @PathVariable Long cafeId,
                                                            @RequestBody @Valid PostCongestionReq postCongestionReq)
            throws CongestionException {

        return new SuccessResponse<>(
                SuccessStatus.CREATE_CONGESTION, congestionService.addAndUpdateCongestion(cafeId, postCongestionReq));

    }

}
