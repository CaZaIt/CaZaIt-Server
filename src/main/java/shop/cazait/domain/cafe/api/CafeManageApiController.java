package shop.cazait.domain.cafe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.model.dto.response.CafeCreateOutDTO;
import shop.cazait.domain.cafe.model.dto.request.CafeCreateInDTO;
import shop.cazait.domain.cafe.model.dto.response.CafeUpdateOutDTO;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.model.dto.response.ManageCafeListOutDTO;
import shop.cazait.domain.cafe.service.CafeManageService;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.global.common.dto.response.SuccessResponse;

import static shop.cazait.global.error.status.SuccessStatus.*;

@Tag(name = "카페 등록 및 수정 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeManageApiController {

    private final CafeManageService cafeManageService;


    @PostMapping(value = "/add/master/{masterId}")
    @Operation(summary = "카페 등록", description = "master가 카페를 등록한다.")
    public SuccessResponse<CafeCreateOutDTO> createCafe(
            @RequestBody CafeCreateInDTO cafeCreateInDTO
    ) throws JsonProcessingException {
        return new SuccessResponse<>(CREATE_CAFE, cafeManageService.createCafe(cafeCreateInDTO));
    }


    @PostMapping("/update/{cafeId}/master/{masterId}")
    @Operation(summary = "카페 정보 수정", description = "특정 ID의 카페 정보를 수정한다.")
    @Parameters({
            @Parameter(name = "cafeId", description = "카페 ID"),
            @Parameter(name = "masterId", description = "마스터 ID")
    })
    public SuccessResponse<CafeUpdateOutDTO> updateCafe(@PathVariable Long cafeId,
                                                        @PathVariable UUID masterId,
                                                        @RequestBody @Valid CafeCreateInDTO cafeReq) throws CafeException, JsonProcessingException {
        CafeUpdateOutDTO cafeUpdateOutDTO = cafeManageService.updateCafe(cafeId, masterId, cafeReq);
        return new SuccessResponse<>(SUCCESS, cafeUpdateOutDTO);

    }

    @PostMapping("/delete/{cafeId}/master/{masterId}")
    @Operation(summary = "카페 삭제 (상태 변경)", description = "특정 ID의 카페 상태를 INACTIVE로 변경한다.")
    @Parameters({
            @Parameter(name = "cafeId", description = "카페 ID"),
            @Parameter(name = "masterId", description = "마스터 ID")
    })
    public SuccessResponse<String> deleteCafe(@PathVariable Long cafeId,
                                              @PathVariable UUID masterId) throws CafeException {
        cafeManageService.deleteCafe(cafeId, masterId);
        return new SuccessResponse<>(SUCCESS,"카페 삭제 완료");
    }

    @GetMapping("/{masterId}/cafes")
    @Operation(summary = "관리하고 있는 카페 목록 불러오기", description = "마스터 ID를 받아 관리하고 있는 카페를 조회")
    public SuccessResponse<List<ManageCafeListOutDTO>> getManageCafes(@Validated @PathVariable UUID masterId) throws MasterException {
        return new SuccessResponse(SUCCESS, cafeManageService.getManageCafes(masterId));
    }

}
