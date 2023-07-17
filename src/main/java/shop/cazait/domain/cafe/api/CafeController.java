package shop.cazait.domain.cafe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.dto.CafeCreateOutDTO;
import shop.cazait.domain.cafe.dto.CafeGetOutDTO;
import shop.cazait.domain.cafe.dto.CafeListOutDTO;
import shop.cazait.domain.cafe.dto.CafeCreateInDTO;
import shop.cazait.domain.cafe.dto.CafeUpdateOutDTO;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.service.CafeSearchService;
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;

import static shop.cazait.global.error.status.SuccessStatus.*;

@Tag(name = "카페 등록 및 수정 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;


    @PostMapping(value = "/add/master/{masterId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "카페 등록", description = "master가 카페를 등록한다.")
    public SuccessResponse<CafeCreateOutDTO> createCafe(
            @RequestBody CafeCreateInDTO cafeCreateInDTO
    ) throws JsonProcessingException {
        return new SuccessResponse<>(CREATE_CAFE, cafeService.createCafe(cafeCreateInDTO));
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
        CafeUpdateOutDTO cafeUpdateOutDTO = cafeService.updateCafe(cafeId, masterId, cafeReq);
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
        cafeService.deleteCafe(cafeId, masterId);
        return new SuccessResponse<>(SUCCESS,"카페 삭제 완료");
    }

}
