package shop.cazait.domain.cafe.entity;

import javax.persistence.*;
import lombok.*;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.global.common.status.BaseStatus;

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

    @OneToOne(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Master master;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Builder
    protected Cafe(Congestion congestion, Master master, String name, String location, double longitude, double latitude) {
        this.congestion = congestion;
        this.master = master;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void changeCafeInfo(String name, String location, double longitude, double latitude) {
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void changeCafeStatus(BaseStatus status) {
        this.status = status;   // BaseEntity의 status가 protected일 때 가능
    }

}
