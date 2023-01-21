package shop.cazait.domain.cafe.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.dto.PostCafeImageReq;
import shop.cazait.domain.cafe.service.CafeImageService;
import shop.cazait.global.common.response.BaseResponse;

@Api
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    @PostMapping("/add/{cafeId}")
    @ApiOperation(value = "카페 이미지 등록", notes = "등록된 카페의 이미지를 등록한다.")
    public BaseResponse<String> addCafeImage(@PathVariable Long cafeId, @RequestBody PostCafeImageReq cafeImageReq) {
        this.cafeImageService.addCafeImage(cafeId, cafeImageReq);
        return new BaseResponse<>("카페 이미지 등록 완료");
    }

    @DeleteMapping("delete/{cafeImageId}")
    @ApiOperation(value = "카페 이미지 삭제", notes = "카페 이미지를 삭제한다.")
    public BaseResponse<String> deleteCafeImage(@PathVariable Long cafeImageId) {
        cafeImageService.deleteCafeImage(cafeImageId);
        return new BaseResponse<>("카페 이미지 삭제 완료");
    }
}
