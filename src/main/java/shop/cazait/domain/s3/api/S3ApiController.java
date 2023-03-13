package shop.cazait.domain.s3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.s3.dto.GetPreSignedUrlRes;
import shop.cazait.domain.s3.service.S3Service;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "S3 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3ApiController {

    private final S3Service s3Service;

    @NoAuth
    @GetMapping("/pre-signed")
    @Operation(summary = "미리 서명된 주소 생성", description = "이미지 업로드를 위해 필요한 미리 서명된 주소를 받는다.")
    @Parameter(name = "directory", description = "이미지 종류", example = "cafe")
    public SuccessResponse<GetPreSignedUrlRes> createPreSignedUrl(@RequestParam String directory) {
        return new SuccessResponse<>(SuccessStatus.SUCCESS, s3Service.createPreSignedUrl(directory));
    }

    @NoAuth
    @GetMapping("/object-url")
    @Operation(summary = "객체 주소 조회", description = "업로드 한 이미지 객체의 주소를 받는다.")
    @Parameter(name = "objectKey", description = "객체 키", example = "cafe/caab93eb-e9ed-499e-be61-15cfa1069f73")
    public SuccessResponse<String> getObjectUrl(@RequestParam String objectKey) {
        return new SuccessResponse<>(SuccessStatus.SUCCESS, s3Service.getObjectUrl(objectKey));
    }

}
