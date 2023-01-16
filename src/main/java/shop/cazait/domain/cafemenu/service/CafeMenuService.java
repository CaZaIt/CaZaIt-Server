package shop.cazait.domain.cafemenu.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafemenu.dto.getCafeMenuRes;
import shop.cazait.domain.cafemenu.entity.CafeMenu;
import shop.cazait.domain.cafemenu.repository.CafeMenuRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeMenuService {

    private final CafeMenuRepository cafeMenuRepository;

    /**
     * 카페 메뉴 조회
     */
    public List<getCafeMenuRes> getCafeMenus(Long cafeId) {

        List<CafeMenu> findMenus = cafeMenuRepository.findAllByCafeId(cafeId);
        List<getCafeMenuRes> cafeMenuRes = findMenus.stream()
                .map(cafeMenu -> getCafeMenuRes.builder()
                        .name(cafeMenu.getName())
                        .price(cafeMenu.getPrice())
                        .imageUrl(cafeMenu.getImageUrl())
                        .build()).
                collect(Collectors.toList());


        return cafeMenuRes;
    }


    /**
     * 카페 메뉴 등록
     */

    /**
     * 카페 메뉴 수정
     */

    /**
     * 카페 메뉴 삭제
     */

}
