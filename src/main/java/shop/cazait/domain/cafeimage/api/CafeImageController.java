package shop.cazait.domain.cafeimage.api;

import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.global.common.dto.response.SuccessResponse;

@Api(tags = "카페 이미지 API")
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    @PostMapping("/add/{cafeId}/master/{masterId}")
    @ApiOperation(value = "카페 이미지 등록", notes = "특정 ID를 갖는 카페의 이미지를 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID"),
            @ApiImplicitParam(name = "masterId", value = "마스터 ID")
    })
    public SuccessResponse<String> addCafeImage(@PathVariable Long cafeId,
                                                @PathVariable Long masterId,
                                                @Parameter(description = "카페 이미지") @RequestPart List<MultipartFile> cafeImages) throws CafeException {
        try {
            cafeImageService.addCafeImage(cafeId, masterId, cafeImages);
            return new SuccessResponse<>(SUCCESS,"카페 이미지 등록 완료");
        } catch (CafeException e) {
            throw new CafeException(e.getError());
        }
    }

    @DeleteMapping("delete/{cafeImageId}/master/{masterId}")
    @ApiOperation(value = "카페 이미지 삭제", notes = "특정 ID의 카페 이미지를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeImageId", value = "카페 이미지 ID"),
            @ApiImplicitParam(name = "masterId", value = "마스터 ID")
    })
    public SuccessResponse<String> deleteCafeImage(@PathVariable Long cafeImageId,
                                                   @PathVariable Long masterId) throws CafeImageException {
        try {
            cafeImageService.deleteCafeImage(cafeImageId, masterId);
            return new SuccessResponse<>(SUCCESS,"카페 이미지 삭제 완료");
        } catch (CafeImageException e) {
            throw new CafeImageException(e.getError());
        }
    }
}
