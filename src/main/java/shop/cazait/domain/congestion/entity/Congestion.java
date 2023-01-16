package shop.cazait.domain.congestion.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Congestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "congestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionStatus congestionStatus;

    @Builder
    public Congestion(Cafe cafe, CongestionStatus congestionStatus) {
        this.cafe = cafe;
        this.congestionStatus = congestionStatus;
    }

    public void changeCongestionStatus(CongestionStatus congestionStatus) {
        this.congestionStatus = congestionStatus;
    }

}
