package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@ApiModel(value = "카페 정보 조회 Response", description = "카페 조회 시 얻을 수 있는 정보")
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeRes {
    @JsonProperty
    @ApiModelProperty(value = "카페 ID", example = "1")
    private Long cafeId;
    @JsonProperty
    @ApiModelProperty(value = "혼잡도 ID", example = "1")
    private Long congestionId;
    @JsonProperty
    @ApiModelProperty(value = "이름", example = "롬곡")
    private String name;
    @JsonProperty
    @ApiModelProperty(value = "위치", example = "서울시 중구")
    private String address;
    @JsonProperty
    @ApiModelProperty(value = "경도", example = "127.543215")
    private String longitude;
    @JsonProperty
    @ApiModelProperty(value = "위도", example = "36.987561")
    private String latitude;

    public static GetCafeRes of(Cafe cafe) {
        Congestion congestion = cafe.getCongestion();
        if (congestion == null) {
            congestion = Congestion.builder()
                    .cafe(cafe)
                    .congestionStatus(CongestionStatus.FREE)
                    .build();
        }
        return GetCafeRes.builder()
                .cafeId(cafe.getId())
                .congestionId(congestion.getId())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .longitude(cafe.getCoordinate().getLongitude())
                .latitude(cafe.getCoordinate().getLatitude())
                .build();
    }

}

