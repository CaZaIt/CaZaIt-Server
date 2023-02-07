package shop.cazait.domain.cafemenu.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafemenu.dto.GetCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuRes;
import shop.cazait.domain.cafemenu.service.CafeMenuService;
import shop.cazait.global.common.dto.response.SuccessResponse;

@Api(tags = "카페 메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class CafeMenuApiController {

    private final CafeMenuService cafeMenuService;

    /**
     * 카페 메뉴 등록
     */
    @ApiOperation(value = "카페 메뉴 등록", notes = "카페 ID와 메뉴에 대한 정보를 받아 등록한다.")
    @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    @PostMapping(value ="/cafe/{cafeId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<PostCafeMenuRes> registerMenu(@PathVariable(name = "cafeId") Long cafeId,
                                                         @Parameter(description = "메뉴 정보 : {\"name\": \"아메리카노\", \"description\": \"맛있어!\", \"price\": 4500}")
                                                         @RequestParam @Valid String menuInfo,
                                                         @Parameter(description = "메뉴 이미지") @RequestPart(required = false) MultipartFile menuImage)
            throws CafeException, IOException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostCafeMenuReq postCafeMenuReq = objectMapper.readValue(menuInfo, new TypeReference<>() {});

        return new SuccessResponse<>(cafeMenuService.registerMenu(cafeId, postCafeMenuReq, menuImage));

    }

    @ApiOperation(value = "카페 메뉴 조회", notes = "카페 ID를 받아 해당 카페에 대한 모든 메뉴를 조회한다.")
    @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    @GetMapping("/cafe/{cafeId}")
    public SuccessResponse<List<GetCafeMenuRes>> getMenu(@PathVariable(name = "cafeId") Long cafeId) {

        return new SuccessResponse<>(cafeMenuService.getMenu(cafeId));

    }


    @ApiOperation(value = "카페 메뉴 수정", notes = "카페 메뉴 ID를 받아 수정한다.")
    @ApiImplicitParam(name = "menuId", value = "카페 메뉴 ID")
    @PatchMapping("/{menuId}")
    public SuccessResponse<PatchCafeMenuRes> updateMenu(@PathVariable(name = "cafeId") Long menuId,
                                                        @Parameter(description = "수정할 메뉴 정보 : {\"name\": \"아메리카노\", \"description\": \"맛있어!\", \"price\": 4500}")
                                                        @RequestParam @Valid String menuInfo,
                                                        @Parameter(description = "수정할 메뉴 이미지") @RequestPart(required = false) MultipartFile menuImage) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PatchCafeMenuReq patchCafeMenuReq = objectMapper.readValue(menuInfo, new TypeReference<>() {});

        return new SuccessResponse<>(cafeMenuService.updateMenu(menuId, patchCafeMenuReq, menuImage));

    }

    @ApiOperation(value = "카페 메뉴 삭제", notes = "카페 메뉴 ID를 받아 삭제한다.")
    @ApiImplicitParam(name = "menuId", value = "메뉴 ID")
    @DeleteMapping("/{menuId}")
    public SuccessResponse<String> updateMenu(@PathVariable(name = "menuId") Long menuId) {

        return new SuccessResponse<>(cafeMenuService.deleteMenu(menuId));

    }

}
