package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@Schema(description = "특정 카페 정보 조회 Response : 카페 조회 시 얻을 수 있는 정보")
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeRes {
    @JsonProperty
    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;
    @JsonProperty
    @Schema(description = "혼잡도 ID", example = "1")
    private CongestionStatus congestionStatus;
    @JsonProperty
    @Schema(description = "이름", example = "롬곡")
    private String name;
    @JsonProperty
    @Schema(description = "위치", example = "서울시 중구")
    private String address;
    @JsonProperty
    @Schema(description = "경도", example = "127.543215")
    private String longitude;
    @JsonProperty
    @Schema(description = "위도", example = "36.987561")
    private String latitude;
    @JsonProperty
    @Schema(description = "방문 기록 등록 여부", example = "36.987561")
    private String logResult;

    public static GetCafeRes of(Cafe cafe, String logResult) {
        return GetCafeRes.builder()
                .cafeId(cafe.getId())
                .congestionStatus(cafe.getCongestion().getCongestionStatus())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .longitude(cafe.getCoordinate().getLongitude())
                .latitude(cafe.getCoordinate().getLatitude())
                .build();
    }

}

