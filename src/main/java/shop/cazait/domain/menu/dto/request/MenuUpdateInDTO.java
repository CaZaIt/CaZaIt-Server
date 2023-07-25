package shop.cazait.domain.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "메뉴 수정 Request", description = "수정할 메뉴에 대한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuUpdateInDTO {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "이름", example = "아이스 아메리카노 (미수정 : null)")
    private String name;

    @Schema(description = "설명", example = "맛있는 아메리카노 (미수정 : null)")
    private String description;

    @Schema(description = "가격", example = "4500 (미수정 : null)")
    private Integer price;

    @Schema(description = "이미지 URL", example = "https://cazait-bucket.s3.ap-northeast-2.amazonaws.com/cafe-image/abcdefghijklmnopqrstuvwxyz", required = true)
    private String ImageUrl;


}
