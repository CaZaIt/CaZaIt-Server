package shop.cazait.domain.cafe.api;

import static shop.cazait.global.error.status.SuccessStatus.NO_CONTENT_SUCCESS;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafe.dto.CafeGetOutDTO;
import shop.cazait.domain.cafe.dto.CafeListOutDTO;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.service.CafeSearchService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "카페 조회 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeSearchApiController {

    private final CafeSearchService cafeSearchService;

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
        List<List<CafeListOutDTO>> cafeResList = cafeSearchService.findCafesByStatusNoAuth(longitude, latitude, sort, limit);
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

        List<List<CafeListOutDTO>> cafeResList = cafeSearchService.findCafesByStatus(userId, longitude, latitude, sort, limit);
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
        CafeGetOutDTO cafeRes = cafeSearchService.getCafeNoAuth(cafeId);
        return new SuccessResponse<>(SUCCESS, cafeRes);
    }

    @GetMapping("/id/{cafeId}/user/{userId}")
    @Operation(summary = "카페 ID 조회(토큰 필요 O)", description = "특정 ID의 카페를 조회한다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 ID"),
            @Parameter(name = "cafeId", description = "카페 ID")
    })
    public SuccessResponse<CafeGetOutDTO> getCafe(@PathVariable Long cafeId) throws CafeException, UserException {

        CafeGetOutDTO cafeRes = cafeSearchService.getCafe(cafeId);
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
        List<List<CafeListOutDTO>> cafeResList = cafeSearchService.findCafesByNameNoAuth(cafeName, longitude, latitude, sort, limit);
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

        List<List<CafeListOutDTO>> cafeResList = cafeSearchService.findCafesByName(cafeName, userId, longitude, latitude, sort, limit);
        SuccessStatus resultStatus = SUCCESS;
        if (cafeResList.get(0).isEmpty()) {
            resultStatus = NO_CONTENT_SUCCESS;
        }
        return new SuccessResponse<>(resultStatus ,cafeResList);
    }

}
