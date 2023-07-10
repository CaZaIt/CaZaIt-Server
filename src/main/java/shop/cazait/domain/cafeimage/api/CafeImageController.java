package shop.cazait.domain.cafeimage.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_CAFE_IMAGE;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafeimage.dto.request.CafeImageCreateInDTO;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.cafeimage.service.CafeImageService;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;

@Tag(name = "카페 이미지 API")
@RestController
@RequestMapping("/api/cafes/images")
@RequiredArgsConstructor
public class CafeImageController {

    private final CafeImageService cafeImageService;

    @PostMapping("/object-url")
    @Operation(summary = "카페 이미지 URL 저장", description = "업로드한 카페 이미지에 대한 URL 저장")
    public SuccessResponse<String> createCageImage(
            @RequestBody CafeImageCreateInDTO cafeImageCreateInDTO
            ) {
        return new SuccessResponse<>(CREATE_CAFE_IMAGE, cafeImageService.createCafeImage(cafeImageCreateInDTO));
    }

    @DeleteMapping("/delete/{cafeImageId}/master/{masterId}")
    @Operation(summary = "이미지 삭제", description = "특정 ID의 카페 이미지를 삭제한다.")
    @Parameters({
            @Parameter(name = "cafeImageId", description = "카페 이미지 ID"),
            @Parameter(name = "masterId", description = "마스터 ID")
    })
    public SuccessResponse<String> deleteCafeImage(@PathVariable Long cafeImageId,
                                                   @PathVariable UUID masterId) throws CafeImageException {
        cafeImageService.deleteCafeImage(cafeImageId, masterId);
        return new SuccessResponse<>(SUCCESS,"카페 이미지 삭제 완료");
    }
}
