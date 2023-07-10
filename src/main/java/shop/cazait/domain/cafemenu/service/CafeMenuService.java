package shop.cazait.domain.cafemenu.service;

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
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafemenu.dto.request.MenuUpdateInDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuListOutDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuUpdateOutDTO;
import shop.cazait.domain.cafemenu.dto.request.MenuCreateInDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuCreateOutDTO;
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
    public List<MenuListOutDTO> getMenu(Long cafeId) {

        List<CafeMenu> findMenus = cafeMenuRepository.findAllByCafeId(cafeId).orElse(null);
        return MenuListOutDTO.of(findMenus);

    }


    /**
     * 카페 메뉴 등록
     */
    public MenuCreateOutDTO createMenu(Long cafeId, MenuCreateInDTO menuCreateInDTO, MultipartFile menuImage)
            throws CafeException, IOException {

        String uploadFileName = null;
        Cafe findCafe = getCafe(cafeId);

        if (menuImage != null) {
//            uploadFileName = awsS3Servicel.uploadImage(menuImage);
        }

        CafeMenu menu = MenuCreateInDTO.toEntity(findCafe, menuCreateInDTO, uploadFileName);
        CafeMenu addMenu = cafeMenuRepository.save(menu);
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
    public MenuUpdateOutDTO updateMenu(Long menuId, MenuUpdateInDTO menuUpdateInDTO, MultipartFile menuImage)
            throws IOException {

        CafeMenu findMenu = cafeMenuRepository
                .findById(menuId)
                .orElseThrow(() -> new CafeMenuException(NOT_EXIST_MENU));

        if (menuUpdateInDTO.getName() != NOT_UPDATE_NAME) {
            findMenu.changeName(menuUpdateInDTO.getName());
        }

        if (menuUpdateInDTO.getDescription() != NOT_UPDATE_DESCRIPTION) {
            findMenu.changeDescription(menuUpdateInDTO.getDescription());
        }

        if (menuUpdateInDTO.getPrice() != NOT_UPDATE_PRICE) {
            findMenu.changePrice(menuUpdateInDTO.getPrice());
        }

        if (menuImage.getName() != NOT_UPDATE_IMAGE) {
//            findMenu.changeImageUrl(awsS3Servicel.uploadImage(menuImage));
        }

        CafeMenu updateCafeMenu = cafeMenuRepository.save(findMenu);

        return MenuUpdateOutDTO.of(updateCafeMenu);

    }

    /**
     * 카페 메뉴 삭제
     */
    public String deleteMenu(Long cafeMenuId) {

        CafeMenu findMenu = cafeMenuRepository
                .findById(cafeMenuId)
                .orElseThrow(() -> new CafeMenuException(NOT_EXIST_MENU));

        cafeMenuRepository.delete(findMenu);

        return "메뉴 삭제 완료";

    }

}
