package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "등록 메뉴 Response", description = "등록을 완료한 메뉴 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostCafeMenuRes {

    @ApiModelProperty(value = "ID", example = "1")
    private Long menuId;

    @ApiModelProperty(value = "이름", example = "아메리카노")
    private String name;

    @ApiModelProperty(value = "설명", example = "맛있는 아메리카노")
    private String description;

    @ApiModelProperty(value = "가격", example = "3000")
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "americano.png")
    private String imageUrl;

    public static List<PostCafeMenuRes> of(List<CafeMenu> menus) {

        return menus.stream()
                .map(menu -> PostCafeMenuRes.builder()
                        .menuId(menu.getId())
                        .name(menu.getName())
                        .description(menu.getDescription())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build())
                .collect(Collectors.toList());

    }

}
