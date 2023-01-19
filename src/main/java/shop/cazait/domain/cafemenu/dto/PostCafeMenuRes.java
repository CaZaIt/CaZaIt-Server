package shop.cazait.domain.cafemenu.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Builder(access = AccessLevel.PRIVATE)
public class PostCafeMenuRes {

    private Long menuId;
    private String name;
    private int price;
    private String imageUrl;

    public static List<PostCafeMenuRes> of(List<CafeMenu> menus) {

        return menus.stream()
                .map(menu -> PostCafeMenuRes.builder()
                        .menuId(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build())
                .collect(Collectors.toList());

    }

}
