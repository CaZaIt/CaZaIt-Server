package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "카페 메뉴 등록", description = "등록할 메뉴에 대한 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class PostCafeMenuReq {

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "이미지 URL")
    private String imageUrl;

    public static List<CafeMenu> toEntity(Cafe cafe, List<PostCafeMenuReq> postCafeMenuReqs) {
        return postCafeMenuReqs.stream()
                .map(postCafeMenuReq -> CafeMenu.builder()
                        .cafe(cafe)
                        .name(postCafeMenuReq.getName())
                        .price(postCafeMenuReq.getPrice())
                        .imageUrl(postCafeMenuReq.getImageUrl())
                        .build()).collect(Collectors.toList());
    }

}
