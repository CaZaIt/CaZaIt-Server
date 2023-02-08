package shop.cazait.domain.coordinate.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coordinate {

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Builder
    protected Coordinate(String longitude, String latitude) {

        this.longitude = longitude;
        this.latitude = latitude;

    }

}
