package shop.cazait.domain.cafemenu.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafemenu.dto.PostCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PutCafeMenuReq;
import shop.cazait.domain.cafemenu.dto.PutCafeMenuRes;
import shop.cazait.domain.cafemenu.dto.GetCafeMenuRes;
import shop.cazait.domain.cafemenu.entity.CafeMenu;
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
    public List<GetCafeMenuRes> getCafeMenus(Long cafeId) {

        List<CafeMenu> findMenus = cafeMenuRepository.findAllByCafeId(cafeId);

        return GetCafeMenuRes.of(findMenus);

    }


    /**
     * 카페 메뉴 등록
     */
    public void addCafeMenu(Long cafeId, List<PostCafeMenuReq> postCafeMenuReqs) {

        Cafe findCafe = cafeRepository.findById(cafeId);
        List<CafeMenu> cafeMenus = postCafeMenuReqs.stream()
                .map(postCafeMenuReq -> CafeMenu.builder()
                        .cafe(findCafe)
                        .name(postCafeMenuReq.getName())
                        .price(postCafeMenuReq.getPrice())
                        .imageUrl(postCafeMenuReq.getImageUrl())
                        .build()).collect(Collectors.toList());

        cafeMenuRepository.saveAll(cafeMenus);

    }

    /**
     * 카페 메뉴 수정
     */
    public PutCafeMenuRes updateCafeMenu(Long cafeId, Long cafeMenuId, PutCafeMenuReq putCafeMenuReq) {

        CafeMenu findCafeMenu = cafeMenuRepository.findByMenuAndCafe(cafeMenuId, cafeId);

        if (putCafeMenuReq.getName() != null) {
            findCafeMenu.changeCafeMenuName(putCafeMenuReq.getName());
        }

        if (putCafeMenuReq.getPrice() != -1) {
            findCafeMenu.changeCafeMenuPrice(putCafeMenuReq.getPrice());
        }

        if (putCafeMenuReq.getImageUrl() != null) {
            findCafeMenu.changeCafeMenuImageUrl(putCafeMenuReq.getImageUrl());
        }

        CafeMenu updateCafeMenu = cafeMenuRepository.save(findCafeMenu);

        return PutCafeMenuRes.of(updateCafeMenu);

    }

    /**
     * 카페 메뉴 삭제
     */
    public void deleteCafeMenu(Long cafeMenuId) {
        cafeMenuRepository.deleteById(cafeMenuId);
    }

}
