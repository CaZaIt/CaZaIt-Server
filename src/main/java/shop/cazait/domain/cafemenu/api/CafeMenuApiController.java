package shop.cazait.domain.cafemenu.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafemenu.dto.GetCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuRes;
import shop.cazait.domain.cafemenu.service.CafeMenuService;
import shop.cazait.global.common.response.SuccessResponse;

@Api(tags = "카페 메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class CafeMenuApiController {

    private final CafeMenuService cafeMenuService;

    /**
     * 카페 메뉴 등록
     */
    @ApiOperation(value = "카페 메뉴 등록", notes = "카페 ID와 메뉴에 대한 정보를 받아 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    @PostMapping("/cafe/{cafeId}")
    public SuccessResponse<List<PostCafeMenuRes>> registerMenu(@PathVariable(name = "cafeId") Long cafeId,
                                                            @RequestBody List<PostCafeMenuReq> postCafeMenuReq)
            throws CafeException {

        // 이름 확인
        // 가격 확인
        // 이미지 확인

        List<PostCafeMenuRes> result = cafeMenuService.registerMenu(cafeId, postCafeMenuReq);
        return new SuccessResponse<>(result);

    }

    /**
     * 카페 메뉴 조회
     */
    @ApiOperation(value = "카페 메뉴 조회", notes = "카페 ID를 받아 해당 카페에 대한 모든 메뉴 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    @GetMapping("/cafe/{cafeId}")
    public SuccessResponse<List<GetCafeMenuRes>> getMenu(@PathVariable(name = "cafeId") Long cafeId) {

        List<GetCafeMenuRes> result = cafeMenuService.getMenu(cafeId);
        return new SuccessResponse<>(result);

    }

    /**
     * 카페 메뉴 수정
     */
    @ApiOperation(value = "카페 메뉴 수정", notes = "카페 ID, 카페 메뉴 ID를 받아 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "카페 메뉴 ID"),
    })
    @PatchMapping("/{menuId}/cafe/{cafeId}")
    public SuccessResponse<PatchCafeMenuRes> updateMenu(@PathVariable(name = "menuId") Long menuId,
                                                     @RequestBody PatchCafeMenuReq patchCafeMenuReq) {

        PatchCafeMenuRes result = cafeMenuService.updateMenu(menuId, patchCafeMenuReq);
        return new SuccessResponse<>(result);

    }

    /**
     * 카페 메뉴 삭제
     */
    @ApiOperation(value = "카페 메뉴 삭제", notes = "카페 메뉴 ID를 받아 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "메뉴 ID")
    })
    @DeleteMapping("/{menuId}")
    public SuccessResponse<String> updateMenu(@PathVariable(name = "menuId") Long menuId) {

        String result = cafeMenuService.deleteMenu(menuId);
        return new SuccessResponse<>(result);

    }

}
