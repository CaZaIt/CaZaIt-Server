package shop.cazait.domain.cafecongestion.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.global.common.entity.baseEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class cafeCongestion extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false, unique = true)
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private congestionStatus status;

}
