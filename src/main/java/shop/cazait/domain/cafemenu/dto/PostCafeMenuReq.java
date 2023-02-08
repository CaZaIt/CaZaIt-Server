package shop.cazait.domain.cafemenu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafemenu.entity.CafeMenu;

@Schema(description = "메뉴 등록 Request : 등록할 메뉴에 대한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeMenuReq {

    @Schema(description = "이름", example = "아이스 아메리카노", required = true)
    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노")
    private String description;

    @Schema(description = "가격", example = "4500", required = true)
    @NotBlank(message = "메뉴 가격을 입력해주세요.")
    private Integer price;

    public static CafeMenu toEntity(Cafe cafe, PostCafeMenuReq postCafeMenuReq, String uploadFileName) {
        return CafeMenu.builder()
                .cafe(cafe)
                .name(postCafeMenuReq.getName())
                .description(postCafeMenuReq.getDescription())
                .price(postCafeMenuReq.getPrice())
                .imageUrl(uploadFileName)
                .build();
    }

}
