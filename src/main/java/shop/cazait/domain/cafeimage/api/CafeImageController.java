package shop.cazait.domain.cafeimage.api;

import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "카페 이미지 API")
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    // todo: 이미지 등록은 카페 수정으로 대체해도 될 듯!
    @PostMapping("/add/{cafeId}/master/{masterId}")
    @Operation(summary = "카페 이미지 등록", description = "특정 ID를 갖는 카페의 이미지를 등록한다.")
    @Parameters({
            @Parameter(name = "cafeId", description = "카페 ID"),
            @Parameter(name = "masterId", description = "마스터 ID")
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
    @Operation(summary = "카페 이미지 삭제", description = "특정 ID의 카페 이미지를 삭제한다.")
    @Parameters({
            @Parameter(name = "cafeImageId", description = "카페 이미지 ID"),
            @Parameter(name = "masterId", description = "마스터 ID")
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
