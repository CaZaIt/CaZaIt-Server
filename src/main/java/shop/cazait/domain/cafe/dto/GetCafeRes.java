package shop.cazait.domain.cafe.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCafeRes {
    private Long cafeId;
    private Long congestionId;
    private String name;
    private String location;
    private double longitude;
    private double latitude;

    @Builder
    public GetCafeRes(Long cafeId, Long congestionId, String name, String location, double longitude, double latitude) {
        this.cafeId = cafeId;
        this.congestionId = congestionId;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
