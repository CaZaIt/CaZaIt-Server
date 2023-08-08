package shop.cazait.domain.cafe.model.entity;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.cafe.model.dto.request.CafeCreateInDTO;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.coordinate.entity.Coordinate;
import shop.cazait.domain.master.model.entity.Master;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.global.common.status.BaseStatus;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "congestion_id")
    private Congestion congestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
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
