package shop.cazait.domain.cafe.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Schema(description = "카페 등록  Request : 카페 등록  필요한 정보")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeCreateInDTO {

    @Schema(description = "마스터 ID", example = "dsak-0dmks-0dskd")
    private UUID masterId;

    @Schema(description = "이름", example = "롬곡")
    @NotBlank(message = "카페 이름을 입력해주세요.")
    private String name;

    @Schema(description = "주소", example = "서울특별시 광진구 군자동 광나루로17길 18")
    @NotBlank(message = "카페 위치를 입력해주세요.")
    private String address;

    @Builder
    public CafeCreateInDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
