package shop.cazait.domain.cafemenu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "메뉴 수정 Request", description = "수정할 메뉴에 대한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuUpdateInDTO {

    @Schema(description = "이름", example = "아이스 아메리카노 (미수정 : null)")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노 (미수정 : null)")
    private String description;

    @Schema(description = "가격", example = "4500 (미수정 : null)")
    private Integer price;

}
