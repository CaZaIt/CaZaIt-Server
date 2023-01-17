package shop.cazait.domain.cafe.dto;

import lombok.*;
import shop.cazait.domain.congestion.entity.Congestion;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCafeReq {
    private Congestion congestion;
    private String name;
    private String location;
    private double longitude;
    private double latitude;

    @Builder
    public PostCafeReq(Congestion congestion, String name, String location, double longitude, double latitude) {
        this.congestion = congestion;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
