package shop.cazait.domain.cafemenu.service;

import static shop.cazait.domain.cafemenu.exception.CafeMenuErrorStatus.INVALID_MENU;
import static shop.cazait.domain.cafemenu.exception.CafeMenuErrorStatus.NOT_REGISTER_MENU;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_IMAGE;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_NAME;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_PRICE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PatchCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.GetCafeMenuRes;
import shop.cazait.domain.cafemenu.entity.CafeMenu;
import shop.cazait.domain.cafemenu.exception.CafeMenuException;
import shop.cazait.domain.cafemenu.repository.CafeMenuRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeMenuService {

    private final CafeRepository cafeRepository;
    private final CafeMenuRepository cafeMenuRepository;

    /**
     * 카페 메뉴 조회
     */
    @Transactional(readOnly = true)
    public List<GetCafeMenuRes> getMenu(Long cafeId) {

        List<CafeMenu> findMenus = cafeMenuRepository.findAllByCafeId(cafeId);
        return GetCafeMenuRes.of(findMenus);

    }


    /**
     * 카페 메뉴 등록
     *
     */
    public List<PostCafeMenuRes> registerMenu(Long cafeId, List<PostCafeMenuReq> postCafeMenuReqs) {

        Cafe findCafe = cafeRepository.findById(cafeId);
        List<CafeMenu> menus = PostCafeMenuReq.toEntity(findCafe, postCafeMenuReqs);
        List<CafeMenu> addMenus = cafeMenuRepository.saveAll(menus);

        return PostCafeMenuRes.of(addMenus);

    }

    /**
     * 카페 메뉴 수정
     */
    public PatchCafeMenuRes updateMenu(Long cafeId, Long cafeMenuId, PatchCafeMenuReq patchCafeMenuReq) {

        CafeMenu findMenu = cafeMenuRepository.findByMenuAndCafe(cafeMenuId, cafeId);

        if (patchCafeMenuReq.getName() != NOT_UPDATE_NAME) {
            findMenu.changeCafeMenuName(patchCafeMenuReq.getName());
        }

        if (patchCafeMenuReq.getPrice() != NOT_UPDATE_PRICE) {
            findMenu.changeCafeMenuPrice(patchCafeMenuReq.getPrice());
        }

        if (patchCafeMenuReq.getImageUrl() != NOT_UPDATE_IMAGE) {
            findMenu.changeCafeMenuImageUrl(patchCafeMenuReq.getImageUrl());
        }

        CafeMenu updateCafeMenu = cafeMenuRepository.save(findMenu);

        return PatchCafeMenuRes.of(updateCafeMenu);

    }

    /**
     * 카페 메뉴 삭제
     */
    public String deleteMenu(Long cafeMenuId) {

        CafeMenu findMenu = cafeMenuRepository.findById(cafeMenuId).get();

        try {
            cafeMenuRepository.delete(findMenu);
        } catch (IllegalArgumentException exception) {
            throw new CafeMenuException(INVALID_MENU);
        }

        return "메뉴 삭제 완료";

    }

}
