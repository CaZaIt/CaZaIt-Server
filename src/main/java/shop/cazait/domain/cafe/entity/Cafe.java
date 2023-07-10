package shop.cazait.domain.cafe.entity;

import javax.persistence.*;
import lombok.*;
import shop.cazait.domain.cafe.dto.CafeCreateInDTO;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.coordinate.entity.Coordinate;
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
    private String address;

    @Embedded
    private Coordinate coordinate;

    @OneToMany(mappedBy = "cafe")
    private List<CafeImage> cafeImage;

    @Builder
    protected Cafe(Congestion congestion, Master master, String name, String address, Coordinate coordinate) {
        this.congestion = congestion;
        this.master = master;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
    }

    public void initCongestion(Congestion congestion) {
        this.congestion = congestion;
    }

    public void updateCongestion(Congestion congestion) {
        this.congestion = congestion;
    }

    public void updateInformation(CafeCreateInDTO cafeCreateInDTO, Coordinate coordinate) {
        this.name = cafeCreateInDTO.getName();
        this.address = cafeCreateInDTO.getAddress();
        this.coordinate = coordinate;
    }

    public void changeStatus(BaseStatus status) {
        super.setStatus(status);
    }


}
