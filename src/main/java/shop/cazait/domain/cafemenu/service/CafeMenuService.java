package shop.cazait.domain.cafemenu.service;

import static shop.cazait.domain.cafe.error.CafeErrorStatus.NON_EXIST_CAFE;
import static shop.cazait.domain.cafemenu.exception.CafeMenuErrorStatus.INVALID_MENU;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_IMAGE;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_NAME;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_PRICE;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.error.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
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

        List<CafeMenu> findMenus = cafeMenuRepository.findAllByCafeId(cafeId).orElse(null);
        return GetCafeMenuRes.of(findMenus);

    }


    /**
     * 카페 메뉴 등록
     */
    public List<PostCafeMenuRes> registerMenu(Long cafeId, List<PostCafeMenuReq> postCafeMenuReqs)
            throws CafeException {

        Cafe findCafe = getCafe(cafeId);
        List<CafeMenu> menus = PostCafeMenuReq.toEntity(findCafe, postCafeMenuReqs);
        List<CafeMenu> addMenus = cafeMenuRepository.saveAll(menus);

        return PostCafeMenuRes.of(addMenus);

    }

    private Cafe getCafe(Long cafeId) throws CafeException {
        try {
            return cafeRepository.getReferenceById(cafeId);    
        } catch (EntityNotFoundException exception) {
            throw new CafeException(NON_EXIST_CAFE);
        }
        
    }

    /**
     * 카페 메뉴 수정
     */
    public PatchCafeMenuRes updateMenu(Long cafeMenuId, PatchCafeMenuReq patchCafeMenuReq) {

        CafeMenu findMenu = cafeMenuRepository
                .findById(cafeMenuId)
                .orElseThrow(() -> new CafeMenuException(INVALID_MENU));

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

        CafeMenu findMenu = cafeMenuRepository
                .findById(cafeMenuId)
                .orElseThrow(() -> new CafeMenuException(INVALID_MENU));

        cafeMenuRepository.delete(findMenu);

        return "메뉴 삭제 완료";

    }

}
