package shop.cazait.domain.cafe.entity;

import javax.persistence.*;
import lombok.*;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.congestion.entity.Congestion;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congestion_id")
    private Congestion congestion;

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
    protected Cafe(Congestion congestion, String name, String location, double longitude, double latitude, String imageUrl) {
        this.congestion = congestion;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrl = imageUrl;
    }

}
