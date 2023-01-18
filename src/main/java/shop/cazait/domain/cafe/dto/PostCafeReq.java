package shop.cazait.domain.cafe.dto;

import lombok.*;
import shop.cazait.domain.congestion.entity.Congestion;

@Getter
public class PostCafeReq {
    private final Congestion congestion;
    private final String name;
    private final String location;
    private final double longitude;
    private final double latitude;

    @Builder
    public PostCafeReq(Congestion congestion, String name, String location, double longitude, double latitude) {
        this.congestion = congestion;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
