package shop.cazait.domain.cafe.entity;

import javax.persistence.*;
import lombok.*;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.cafecongestion.entity.CafeCongestion;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congestion_id")
    private CafeCongestion cafeCongestion;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    protected Cafe(CafeCongestion cafeCongestion, String name, String location, double longitude, double latitude, String imageUrl) {
        this.cafeCongestion = cafeCongestion;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrl = imageUrl;
    }

}
