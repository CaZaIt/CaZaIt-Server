package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.entity.Cafe;

@ApiModel(value = "GetCafeRes / 카페 정보", description = "카페 조회 시 필요한 dto")
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
    private String location;
    @JsonProperty
    @ApiModelProperty(value = "경도", example = "127.543215")
    private double longitude;
    @JsonProperty
    @ApiModelProperty(value = "위도", example = "36.987561")
    private double latitude;

    public static GetCafeRes of(Cafe cafe) {
        return GetCafeRes.builder()
                .cafeId(cafe.getId())
                .congestionId(cafe.getCongestion().getId())
                .name(cafe.getName())
                .location(cafe.getLocation())
                .longitude(cafe.getLongitude())
                .latitude(cafe.getLatitude())
                .build();
    }

}

