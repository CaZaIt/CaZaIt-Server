package shop.cazait.domain.cafe.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "카페 수정 Request : 카페 수정에 필요한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeUpdateInDTO {

    @Schema(description = "이름", example = "롬곡")
    @NotBlank(message = "카페 이름을 입력해주세요.")
    private String name;

    @Schema(description = "주소", example = "서울특별시 광진구 군자동 광나루로17길 18")
    @NotBlank(message = "카페 위치를 입력해주세요.")
    private String address;

    @Builder
    public CafeUpdateInDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
