package shop.cazait.domain.cafeimage.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.dto.PostCafeImageReq;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.global.common.response.SuccessResponse;
import shop.cazait.domain.cafeimage.exception.CafeImageException;

@Api(tags = "카페 이미지 API")
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    @PostMapping("/add/{cafeId}")
    @ApiOperation(value = "카페 이미지 등록", notes = "특정 ID를 갖는 카페의 이미지를 등록한다.")
    public SuccessResponse<String> addCafeImage(@PathVariable Long cafeId, @RequestBody PostCafeImageReq cafeImageReq) throws CafeException {
        try {
            cafeImageService.addCafeImage(cafeId, cafeImageReq);
            return new SuccessResponse<>("카페 이미지 등록 완료");
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @DeleteMapping("delete/{cafeImageId}")
    @ApiOperation(value = "카페 이미지 삭제", notes = "특정 ID의 카페 이미지를 삭제한다.")
    public SuccessResponse<String> deleteCafeImage(@PathVariable Long cafeImageId) throws CafeImageException {
        try {
            cafeImageService.deleteCafeImage(cafeImageId);
            return new SuccessResponse<>("카페 이미지 삭제 완료");
        } catch (CafeImageException e) {
            throw new CafeImageException(e.getError());
        }
    }
}
