package shop.cazait.domain.cafe.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.congestion.entity.CongestionStatus;

import java.util.List;

@Schema(description = "특정 카페 정보 조회 Response : 카페 조회 시 얻을 수 있는 정보")
@Builder(access = AccessLevel.PRIVATE)
public class CafeGetOutDTO {

    @JsonProperty
    @Schema(description = "카페 ID", example = "1")
    private UUID cafeId;

    @JsonProperty
    @Schema(description = "혼잡도 상태", example = "FREE")
    private CongestionStatus congestionStatus;

    @JsonProperty
    @Schema(description = "이름", example = "롬곡")
    private String name;

    @JsonProperty
    @Schema(description = "위치", example = "서울특별시 광진구 군자동 광나루로17길 18")
    private String address;

    @JsonProperty
    @Schema(description = "경도", example = "127.543215")
    private String longitude;

    @JsonProperty
    @Schema(description = "위도", example = "36.987561")
    private String latitude;

    @JsonProperty
    @Schema(description = "이미지 url")
    private List<String> cafeImages;

    public static CafeGetOutDTO of(Cafe cafe, List<String> cafeImages) {
        return CafeGetOutDTO.builder()
                .cafeId(cafe.getId())
                .congestionStatus(cafe.getCongestion().getCongestionStatus())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .longitude(cafe.getCoordinate().getLongitude())
                .latitude(cafe.getCoordinate().getLatitude())
                .cafeImages(cafeImages)
                .build();
    }

}

