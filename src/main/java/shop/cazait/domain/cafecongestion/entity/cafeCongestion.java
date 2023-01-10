package shop.cazait.domain.cafecongestion.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class cafeCongestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    private congestionStatus status;

}
