package shop.cazait.domain.cafecongestion.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false, unique = true)
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongestionStatus status;

    @Builder
    public CafeCongestion(Long id, Cafe cafe, CongestionStatus status) {
        this.id = id;
        this.cafe = cafe;
        this.status = status;
    }

}
