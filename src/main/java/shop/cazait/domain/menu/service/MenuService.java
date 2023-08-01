package shop.cazait.domain.menu.service;

import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_DESCRIPTION;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_IMAGE;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_NAME;
import static shop.cazait.global.common.constant.Constant.NOT_UPDATE_PRICE;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_MENU;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.menu.dto.request.MenuUpdateInDTO;
import shop.cazait.domain.menu.dto.response.MenuListOutDTO;
import shop.cazait.domain.menu.dto.response.MenuUpdateOutDTO;
import shop.cazait.domain.menu.dto.request.MenuCreateInDTO;
import shop.cazait.domain.menu.dto.response.MenuCreateOutDTO;
import shop.cazait.domain.menu.entity.Menu;
import shop.cazait.domain.menu.exception.MenuException;
import shop.cazait.domain.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;

    /**
     * 카페 메뉴 조회
     */
    @Transactional(readOnly = true)
    public List<MenuListOutDTO> getMenu(Long cafeId) {

        List<Menu> findMenus = menuRepository.findAllByCafeId(cafeId).orElse(null);
        return MenuListOutDTO.of(findMenus);

    }


    /**
     * 카페 메뉴 등록
     */
    public MenuCreateOutDTO createMenu(MenuCreateInDTO req) throws CafeException{

        Cafe findCafe = getCafe(req.getCafeId());
        Menu menu = MenuCreateInDTO.toEntity(findCafe, req);
        Menu addMenu = menuRepository.save(menu);
        return MenuCreateOutDTO.of(addMenu);
    }

    private Cafe getCafe(Long cafeId) throws CafeException {
        try {
            Cafe cafe = cafeRepository.findById(cafeId).get();
            return cafe;
        } catch (NoSuchElementException ex) {
            throw new CafeException(NOT_EXIST_CAFE);
        }
    }


    /**
     * 카페 메뉴 수정
     */
    public MenuUpdateOutDTO updateMenu(MenuUpdateInDTO req)
            throws IOException {

        Menu findMenu = menuRepository
                .findById(req.getMenuId())
                .orElseThrow(() -> new MenuException(NOT_EXIST_MENU));

        if (req.getName() != NOT_UPDATE_NAME) {
            findMenu.changeName(req.getName());
        }

        if (req.getDescription() != NOT_UPDATE_DESCRIPTION) {
            findMenu.changeDescription(req.getDescription());
        }

        if (req.getPrice() != NOT_UPDATE_PRICE) {
            findMenu.changePrice(req.getPrice());
        }

        if (req.getImageUrl() != NOT_UPDATE_IMAGE) {
            findMenu.changeImageUrl(req.getImageUrl());
        }

        Menu updateMenu = menuRepository.save(findMenu);

        return MenuUpdateOutDTO.of(updateMenu);

    }

    /**
     * 카페 메뉴 삭제
     */
    public String deleteMenu(Long cafeMenuId) {

        Menu findMenu = menuRepository
                .findById(cafeMenuId)
                .orElseThrow(() -> new MenuException(NOT_EXIST_MENU));

        menuRepository.delete(findMenu);

        return "메뉴 삭제 완료";

    }

}
