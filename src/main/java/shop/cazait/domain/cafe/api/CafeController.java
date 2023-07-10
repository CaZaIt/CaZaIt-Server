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
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;

import static shop.cazait.global.error.status.SuccessStatus.*;

@Tag(name = "카페 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @PostMapping(value = "/add/master/{masterId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "카페 등록", description = "master가 카페를 등록한다.")
    public SuccessResponse<CafeCreateOutDTO> createCafe(
            @RequestBody CafeCreateInDTO cafeCreateInDTO
    ) throws JsonProcessingException {
        return new SuccessResponse<>(CREATE_CAFE, cafeService.createCafe(cafeCreateInDTO));
    }

    @NoAuth
    @GetMapping("/all")
    @Operation(summary = "카페 전체 조회(토큰 필요 X)", description = "ACTIVE한 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "longitude", description = "유저 경도"),
            @Parameter(name = "latitude", description = "유저 위도"),
            @Parameter(name = "sort", description = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @Parameter(name = "limit", description = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<CafeListOutDTO>>> findCafesByStatusNoAuth(@RequestParam String longitude,
                                                                             @RequestParam String latitude,
                                                                             @RequestParam String sort,
                                                                             @RequestParam String limit) {
        List<List<CafeListOutDTO>> cafeResList = cafeService.findCafesByStatusNoAuth(longitude, latitude, sort, limit);
        SuccessStatus resultStatus = SUCCESS;
        if (cafeResList.get(0).isEmpty()) {
            resultStatus = NO_CONTENT_SUCCESS;
        }
        return new SuccessResponse<>(resultStatus ,cafeResList);
    }

    @GetMapping("/all/user/{userId}")
    @Operation(summary = "카페 전체 조회(토큰 필요 O)", description = "ACTIVE한 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 ID"),
            @Parameter(name = "longitude", description = "유저 경도"),
            @Parameter(name = "latitude", description = "유저 위도"),
            @Parameter(name = "sort", description = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @Parameter(name = "limit", description = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<CafeListOutDTO>>> findCafesByStatus(@PathVariable UUID userId,
                                                                       @RequestParam String longitude,
                                                                       @RequestParam String latitude,
                                                                       @RequestParam String sort,
                                                                       @RequestParam String limit)
            throws UserException {

        List<List<CafeListOutDTO>> cafeResList = cafeService.findCafesByStatus(userId, longitude, latitude, sort, limit);
        SuccessStatus resultStatus = SUCCESS;
        if (cafeResList.get(0).isEmpty()) {
            resultStatus = NO_CONTENT_SUCCESS;
        }
        return new SuccessResponse<>(resultStatus ,cafeResList);
    }

    @NoAuth
    @GetMapping("/id/{cafeId}")
    @Operation(summary = "카페 ID 조회(토큰 필요 X)", description = "특정 ID의 카페를 조회한다.")
    @Parameter(name = "cafeId", description = "카페 ID")
    public SuccessResponse<CafeGetOutDTO> getCafeNoAuth(@PathVariable Long cafeId) throws CafeException {
        CafeGetOutDTO cafeRes = cafeService.getCafeNoAuth(cafeId);
        return new SuccessResponse<>(SUCCESS, cafeRes);
    }

    @GetMapping("/id/{cafeId}/user/{userId}")
    @Operation(summary = "카페 ID 조회(토큰 필요 O)", description = "특정 ID의 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 ID"),
            @Parameter(name = "cafeId", description = "카페 ID")
    })
    public SuccessResponse<CafeGetOutDTO> getCafe(@PathVariable Long cafeId) throws CafeException, UserException {

        CafeGetOutDTO cafeRes = cafeService.getCafe(cafeId);
        return new SuccessResponse<>(SUCCESS, cafeRes);
    }

    @NoAuth
    @GetMapping("/name/{cafeName}")
    @Operation(summary = "카페 이름 조회(토큰 필요 X)", description = "특정 이름의 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "cafeName", description = "카페 이름"),
            @Parameter(name = "longitude", description = "유저 경도"),
            @Parameter(name = "latitude", description = "유저 위도"),
            @Parameter(name = "sort", description = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @Parameter(name = "limit", description = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<CafeListOutDTO>>> findCafesByNameNoAuth(@PathVariable String cafeName,
                                                                           @RequestParam String longitude,
                                                                           @RequestParam String latitude,
                                                                           @RequestParam String sort,
                                                                           @RequestParam String limit) throws CafeException {
        List<List<CafeListOutDTO>> cafeResList = cafeService.findCafesByNameNoAuth(cafeName, longitude, latitude, sort, limit);
        SuccessStatus resultStatus = SUCCESS;
        if (cafeResList.get(0).isEmpty()) {
            resultStatus = NO_CONTENT_SUCCESS;
        }
        return new SuccessResponse<>(resultStatus ,cafeResList);
    }

    @GetMapping("/name/{cafeName}/user/{userId}")
    @Operation(summary = "카페 이름 조회(토큰 필요 O)", description = "특정 이름의 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "cafeName", description = "카페 이름"),
            @Parameter(name = "userId", description = "유저 ID"),
            @Parameter(name = "longitude", description = "유저 경도"),
            @Parameter(name = "latitude", description = "유저 위도"),
            @Parameter(name = "sort", description = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @Parameter(name = "limit", description = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<CafeListOutDTO>>> findCafesByName(@PathVariable String cafeName,
                                                                     @PathVariable UUID userId,
                                                                     @RequestParam String longitude,
                                                                     @RequestParam String latitude,
                                                                     @RequestParam String sort,
                                                                     @RequestParam String limit) throws CafeException, UserException {

        List<List<CafeListOutDTO>> cafeResList = cafeService.findCafesByName(cafeName, userId, longitude, latitude, sort, limit);
        SuccessStatus resultStatus = SUCCESS;
        if (cafeResList.get(0).isEmpty()) {
            resultStatus = NO_CONTENT_SUCCESS;
        }
        return new SuccessResponse<>(resultStatus ,cafeResList);
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
