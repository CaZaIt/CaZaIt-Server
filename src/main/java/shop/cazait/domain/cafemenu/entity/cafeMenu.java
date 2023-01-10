package shop.cazait.domain.cafemenu.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class cafeMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cafe_id")
    @Column(nullable = false)
    private Cafe cafe;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String imageUrl;

}
