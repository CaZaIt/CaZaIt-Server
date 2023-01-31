package shop.cazait.domain.cafe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.GetCafesRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.response.SuccessResponse;
import shop.cazait.global.common.status.BaseStatus;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "카페 정보 API")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @PostMapping("/add")
    @ApiOperation(value = "카페 등록", notes = "master가 카페를 등록한다.")
    public SuccessResponse<String> addCafe(@RequestBody @Valid PostCafeReq cafeReq) throws JsonProcessingException {
        cafeService.addCafe(cafeReq);
        return new SuccessResponse<>("카페 등록 완료");
    }

    @GetMapping("/all")
    @ApiOperation(value = "카페 전체 조회", notes = "ACTIVE한 카페를 조회한다.")
    public SuccessResponse<List<GetCafesRes>> getCafeByStatus() throws CafeException {
        try {
            List<GetCafesRes> cafeResList = cafeService.getCafeByStatus(BaseStatus.ACTIVE);
            return new SuccessResponse<>(cafeResList);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @GetMapping("/id/{cafeId}")
    @ApiOperation(value = "카페 ID 조회", notes = "특정 ID의 카페를 조회한다.")
    @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    public SuccessResponse<GetCafeRes> getCafeById(@PathVariable Long userId,
                                                   @PathVariable Long cafeId) throws CafeException, UserException {
        try {
            GetCafeRes cafeRes = cafeService.getCafeById(userId, cafeId);
            return new SuccessResponse<>(cafeRes);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @GetMapping("/name/{cafeName}")
    @ApiOperation(value = "카페 이름 조회", notes = "특정 이름의 카페를 조회한다.")
    public SuccessResponse<List<GetCafesRes>> getCafeByName(@PathVariable String cafeName) throws CafeException {
        try {
            List<GetCafesRes> cafeResList = cafeService.getCafeByName(cafeName);
            return new SuccessResponse<>(cafeResList);
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @PostMapping("/update/{cafeId}")
    @ApiOperation(value = "카페 정보 수정", notes = "특정 ID의 카페 정보를 수정한다.")
    public SuccessResponse<String> updateCafe(@PathVariable Long cafeId, @RequestBody @Valid PostCafeReq cafeReq)
            throws CafeException, JsonProcessingException {
        try {
            cafeService.updateCafe(cafeId, cafeReq);
            return new SuccessResponse<>("카페 수정 완료");
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }

    }

    @PostMapping("/delete/{cafeId}")
    @ApiOperation(value = "카페 삭제 (상태 변경)", notes = "특정 ID의 카페 상태를 INACTIVE로 변경한다.")
    public SuccessResponse<String> deleteCafe(@PathVariable Long cafeId) throws CafeException {
        try {
            cafeService.deleteCafe(cafeId);
            return new SuccessResponse<>("카페 삭제 완료");
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }
}