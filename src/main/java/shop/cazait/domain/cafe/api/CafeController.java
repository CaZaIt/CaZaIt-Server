package shop.cazait.domain.cafe.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.dto.GetCafeRes;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafe.error.CafeErrorStatus;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.service.CafeService;
import shop.cazait.global.common.response.BaseResponse;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @PostMapping("/add")
    @ApiOperation(value = "카페 등록", notes = "master가 카페를 등록한다.")
    public BaseResponse<String> addCafe(@RequestBody PostCafeReq cafeReq) {
        this.cafeService.addCafe(cafeReq);
        return new BaseResponse<>("카페 등록 완료");
    }

    @GetMapping("/all")
    @ApiOperation(value = "카페 전체 조회", notes = "ACTIVE한 카페를 조회한다.")
    public BaseResponse<List<GetCafeRes>> getCafeByStatus() throws CafeException {
        List<GetCafeRes> cafeResList = this.cafeService.getCafeByStatus(BaseStatus.ACTIVE);
        return new BaseResponse<>(cafeResList);
    }

    @GetMapping("/id/{cafeId}")
    @ApiOperation(value = "카페 ID 조회", notes = "특정 ID의 카페를 조회한다.")
    @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    public BaseResponse<GetCafeRes> getCafeById(@PathVariable Long cafeId) {
        GetCafeRes cafeRes = this.cafeService.getCafeById(cafeId);
        return new BaseResponse<>(cafeRes);
    }

    @GetMapping("/name/{cafeName}")
    @ApiOperation(value = "카페 이름 조회", notes = "특정 이름의 카페를 조회한다.")
    public BaseResponse<List<GetCafeRes>> getCafeByName(@PathVariable String cafeName) {
        List<GetCafeRes> cafeResList = this.cafeService.getCafeByName(cafeName);
        return new BaseResponse<>(cafeResList);
    }

    @PostMapping("/update/{cafeId}")
    @ApiOperation(value = "카페 정보 수정", notes = "특정 ID의 카페 정보를 수정한다.")
    public BaseResponse<String> updateCafe(@PathVariable Long cafeId, @RequestBody PostCafeReq cafeReq) {
        this.cafeService.updateCafe(cafeId, cafeReq);
        return new BaseResponse<>("카페 수정 완료");
    }

    @PostMapping("/delete/{cafeId}")
    @ApiOperation(value = "카페 삭제 (상태 변경)", notes = "특정 ID의 카페 상태를 INACTIVE로 변경한다.")
    public BaseResponse<String> deleteCafe(@PathVariable Long cafeId) {
        this.cafeService.deleteCafe(cafeId);
        return new BaseResponse<>("카페 삭제 완료");
    }
}