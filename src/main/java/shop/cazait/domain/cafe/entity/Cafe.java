package shop.cazait.domain.cafe.entity;

import javax.persistence.*;
import lombok.*;
import shop.cazait.domain.cafe.dto.PostCafeReq;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "cafe")
    private List<CafeImage> cafeImage;

    @Builder
    protected Cafe(Congestion congestion, Master master, String name, String location, double longitude, double latitude) {
        this.congestion = congestion;
        this.master = master;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void changeCafeInfo(PostCafeReq postCafeReq) {
        this.name = postCafeReq.getName();
        this.location = postCafeReq.getLocation();
        this.longitude = postCafeReq.getLongitude();
        this.latitude = postCafeReq.getLatitude();
    }

    public void changeCafeStatus(BaseStatus status) {
        super.setStatus(status);
    }

}
