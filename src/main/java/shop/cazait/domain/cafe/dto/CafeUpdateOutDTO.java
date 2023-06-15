package shop.cazait.domain.cafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafeimage.dto.CafeImageGetOutDTO;
import shop.cazait.domain.congestion.entity.CongestionStatus;

import java.util.List;
@Schema(description = "카페 등록 및 수정 Response : 카페 등록 및 수정 후 받는 응답")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeUpdateOutDTO {
    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;
    @Schema(description = "혼잡도 상태", example = "FREE")
    private CongestionStatus congestionStatus;
    @Schema(description = "이름", example = "롬곡")
    private String name;
    @Schema(description = "위치", example = "서울특별시 광진구 군자동 광나루로17길 18")
    private String address;
    @Schema(description = "이미지 url")
    private List<CafeImageGetOutDTO> cafeImageRes;

    public static CafeUpdateOutDTO of(Cafe cafe, List<CafeImageGetOutDTO> cafeImageGetOutDTOList) {
        return CafeUpdateOutDTO.builder()
                .cafeId(cafe.getId())
                .congestionStatus(cafe.getCongestion().getCongestionStatus())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .cafeImageRes(cafeImageGetOutDTOList)
                .build();
    }
}
