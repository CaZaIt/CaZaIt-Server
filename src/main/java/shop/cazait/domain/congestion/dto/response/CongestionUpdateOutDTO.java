package shop.cazait.domain.congestion.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.congestion.entity.Congestion;

@Schema(description = "혼잡도 등록(수정) Response : 등록 또는 수정 완료한 혼잡도 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CongestionUpdateOutDTO {

    @Schema(description = "혼잡도 ID", example = "1")
    private Long congestionId;

    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @Schema(description = "혼잡도 상태", example = "FREE")
    private String congestionStatus;

    public static CongestionUpdateOutDTO of(Congestion newCongestion) {

        return CongestionUpdateOutDTO.builder()
                .congestionId(newCongestion.getId())
                .cafeId(newCongestion.getCafe().getId())
                .congestionStatus(newCongestion.getCongestionStatus().getValue())
                .build();
    }

}
