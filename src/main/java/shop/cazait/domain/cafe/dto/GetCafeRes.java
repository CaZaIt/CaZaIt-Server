package shop.cazait.domain.cafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;

@Builder(access = AccessLevel.PRIVATE)
public class GetCafeRes {
    @JsonProperty
    private Long cafeId;
    @JsonProperty
    private Long congestionId;
    @JsonProperty
    private String name;
    @JsonProperty
    private String location;
    @JsonProperty
    private double longitude;
    @JsonProperty
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

