package shop.cazait.domain.cafecongestion.entity;

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
public class CafeCongestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cafeCongestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionStatus status;

    @Builder
    public CafeCongestion(Cafe cafe, CongestionStatus status) {
        this.cafe = cafe;
        this.status = status;
    }

}
