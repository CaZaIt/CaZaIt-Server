package shop.cazait.domain.cafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.global.common.entity.BaseEntity;
import shop.cazait.domain.cafecongestion.entity.CafeCongestion;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Cafe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congestion_id")
    @Column(nullable = false)
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

}
