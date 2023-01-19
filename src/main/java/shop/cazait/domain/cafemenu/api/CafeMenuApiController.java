package shop.cazait.domain.cafemenu.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafemenu.dto.GetCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PutCafeMenuRes;
import shop.cazait.domain.cafemenu.service.CafeMenuService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class CafeMenuApiController {

    private final CafeMenuService cafeMenuService;

    /**
     * 카페 메뉴 등록
     */
    @PostMapping("/cafe/{cafeId}")
    public BaseResponse<List<PostCafeMenuRes>> registerMenu(@PathVariable(name = "cafeId") Long cafeId,
                                                            @RequestBody List<PostCafeMenuReq> postCafeMenuReq) {

        // 이름 확인
        // 가격 확인
        // 이미지 확인

        List<PostCafeMenuRes> result = cafeMenuService.registerMenu(cafeId, postCafeMenuReq);
        return new BaseResponse<>(result);

    }

    /**
     * 카페 메뉴 조회
     */
    @GetMapping("/cafe/{cafeId}")
    public BaseResponse<List<GetCafeMenuRes>> getMenu(@PathVariable(name = "cafeId") Long cafeId) {

        return cafeMenuService.getCafeMenus(cafeId);

    }

    /**
     * 카페 메뉴 수정
     */
    @PutMapping("/{menuId}")
    public BaseResponse<PutCafeMenuRes> updateMenu(@PathVariable(name = "menuId") Long menuId) {

        return cafeMenuService.updateCafeMenu(menuId);

    }

    /**
     * 카페 메뉴 삭제
     */
    @DeleteMapping("/{menuId}")
    public BaseResponse<PutCafeMenuRes> updateMenu(@PathVariable(name = "menuId") Long menuId) {

        return cafeMenuService.deleteCafeMenu(menuId);

    }

}
