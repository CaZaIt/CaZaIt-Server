package shop.cazait.domain.cafemenu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@ApiModel(value = "메뉴 등록 Request", description = "등록할 메뉴에 대한 정보")
@Data
@NoArgsConstructor
public class PostCafeMenuReq {

    @ApiModelProperty(value = "이름", example = "아이스 아메리카노", required = true)
    private String name;

    @ApiModelProperty(value = "가격", example = "4500", required = true)
    private int price;

    @ApiModelProperty(value = "이미지 URL", example = "americano.png(미등록 : null)")
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
