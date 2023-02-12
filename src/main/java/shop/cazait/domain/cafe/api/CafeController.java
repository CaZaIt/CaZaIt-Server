package shop.cazait.domain.cafe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.GetCafesRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.dto.PostCafeRes;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;

import static shop.cazait.global.error.status.SuccessStatus.*;

@Api(tags = "카페 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @PostMapping(value = "/add/master/{masterId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "카페 등록", notes = "master가 카페를 등록한다.")
    @ApiImplicitParam(name = "masterId", value = "마스터 ID")
    public SuccessResponse<PostCafeRes> addCafe(@PathVariable Long masterId,
                                                @Parameter(description = "카페 정보 : {\"name\": \"보난자\", \"address\": \"서울 광진구 능동로 239-1 B동 1층\"")
                                           @RequestParam @Valid String cafeInfo,
                                                @Parameter(description = "카페 이미지") @RequestPart(required = false) List<MultipartFile> cafeImages)
            throws JsonProcessingException {
        PostCafeReq postCafeReq = objectMapper.readValue(cafeInfo, new TypeReference<>() {});
        PostCafeRes postCafeRes = cafeService.addCafe(masterId, postCafeReq, cafeImages);
        return new SuccessResponse<>(CREATE_CAFE, postCafeRes);
    }

    @GetMapping("/all/user/{userId}")
    @ApiOperation(value = "카페 전체 조회", notes = "ACTIVE한 카페를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID"),
            @ApiImplicitParam(name = "longitude", value = "유저 경도"),
            @ApiImplicitParam(name = "latitude", value = "유저 위도"),
            @ApiImplicitParam(name = "sort", value = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @ApiImplicitParam(name = "limit", value = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<GetCafesRes>>> getCafeByStatus(@PathVariable Long userId,
                                                                    @RequestParam String longitude,
                                                                    @RequestParam String latitude,
                                                                    @RequestParam String sort,
                                                                    @RequestParam String limit)
            throws CafeException {
        try {
            List<List<GetCafesRes>> cafeResList = cafeService.getCafeByStatus(userId, longitude, latitude, sort, limit);
            SuccessStatus resultStatus = SUCCESS;
            if (cafeResList.get(0).isEmpty()) {
                resultStatus = NO_CONTENT_SUCCESS;
            }
            return new SuccessResponse<>(resultStatus ,cafeResList);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @GetMapping("/id/{cafeId}/user/{userId}")
    @ApiOperation(value = "카페 ID 조회", notes = "특정 ID의 카페를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID"),
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    public SuccessResponse<GetCafeRes> getCafeById(@PathVariable Long userId,
                                                   @PathVariable Long cafeId) throws CafeException, UserException {
        try {
            GetCafeRes cafeRes = cafeService.getCafeById(userId, cafeId);
            return new SuccessResponse<>(SUCCESS, cafeRes);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @GetMapping("/name/{cafeName}/user/{userId}")
    @ApiOperation(value = "카페 이름 조회", notes = "특정 이름의 카페를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeName", value = "카페 이름"),
            @ApiImplicitParam(name = "userId", value = "유저 ID"),
            @ApiImplicitParam(name = "longitude", value = "유저 경도"),
            @ApiImplicitParam(name = "latitude", value = "유저 위도"),
            @ApiImplicitParam(name = "sort", value = "정렬 기준(congestion: 혼잡도순, distance: 거리순)"),
            @ApiImplicitParam(name = "limit", value = "제한 거리(0일 때는 모든 카페 출력) : 해당 거리 내에 있는 카페 전달, 단위는 m(미터)")
    })
    public SuccessResponse<List<List<GetCafesRes>>> getCafeByName(@PathVariable String cafeName,
                                                                  @PathVariable Long userId,
                                                                  @RequestParam String longitude,
                                                                  @RequestParam String latitude,
                                                                  @RequestParam String sort,
                                                                  @RequestParam String limit) throws CafeException {
        try {
            List<List<GetCafesRes>> cafeResList = cafeService.getCafeByName(cafeName, userId, longitude, latitude, sort, limit);
            SuccessStatus resultStatus = SUCCESS;
            if (cafeResList.get(0).isEmpty()) {
                resultStatus = NO_CONTENT_SUCCESS;
            }
            return new SuccessResponse<>(resultStatus ,cafeResList);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @PostMapping("/update/{cafeId}/master/{masterId}")
    @ApiOperation(value = "카페 정보 수정", notes = "특정 ID의 카페 정보를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID"),
            @ApiImplicitParam(name = "masterId", value = "마스터 ID")
    })
    public SuccessResponse<PostCafeRes> updateCafe(@PathVariable Long cafeId,
                                              @PathVariable Long masterId,
                                              @RequestBody @Valid PostCafeReq cafeReq) throws CafeException, JsonProcessingException {
        try {
            PostCafeRes postCafeRes = cafeService.updateCafe(cafeId, masterId, cafeReq);
            return new SuccessResponse<>(SUCCESS, postCafeRes);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }

    }

    @PostMapping("/delete/{cafeId}/master/{masterId}")
    @ApiOperation(value = "카페 삭제 (상태 변경)", notes = "특정 ID의 카페 상태를 INACTIVE로 변경한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID"),
            @ApiImplicitParam(name = "masterId", value = "마스터 ID")
    })
    public SuccessResponse<String> deleteCafe(@PathVariable Long cafeId,
                                              @PathVariable Long masterId) throws CafeException {
        try {
            cafeService.deleteCafe(cafeId, masterId);
            return new SuccessResponse<>(SUCCESS,"카페 삭제 완료");
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }
}
