package shop.cazait.domain.cafeimage.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafeimage.dto.PostCafeImageReq;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.global.common.response.BaseResponse;
import shop.cazait.global.error.exception.BaseException;

@Api
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    @PostMapping("/add/{cafeId}")
    @ApiOperation(value = "카페 이미지 등록", notes = "등록된 카페의 이미지를 등록한다.")
    public BaseResponse<String> addCafeImage(@PathVariable Long cafeId, @RequestBody PostCafeImageReq cafeImageReq) throws BaseException {
        try {
            this.cafeImageService.addCafeImage(cafeId, cafeImageReq);
            return new BaseResponse<>("카페 이미지 등록 완료");
        } catch (BaseException e) {
            throw new BaseException(e.getError());
        }
    }

    @DeleteMapping("delete/{cafeImageId}")
    @ApiOperation(value = "카페 이미지 삭제", notes = "카페 이미지를 삭제한다.")
    public BaseResponse<String> deleteCafeImage(@PathVariable Long cafeImageId) throws BaseException {
        try {
            this.cafeImageService.deleteCafeImage(cafeImageId);
            return new BaseResponse<>("카페 이미지 삭제 완료");
        } catch (BaseException e) {
            throw new BaseException(e.getError());
        }
    }
}
