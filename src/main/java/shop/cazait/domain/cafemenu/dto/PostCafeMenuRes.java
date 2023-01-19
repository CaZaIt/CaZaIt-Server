package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "등록할 메뉴", description = "등록을 완료한 메뉴 정보")
@Builder(access = AccessLevel.PRIVATE)
public class PostCafeMenuRes {

    @ApiModelProperty(value = "ID")
    private Long menuId;

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "이미지 URL")
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
