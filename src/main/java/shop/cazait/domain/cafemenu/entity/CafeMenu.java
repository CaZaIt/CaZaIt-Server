package shop.cazait.domain.cafemenu.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CafeMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String imageUrl;

    @Builder
    public CafeMenu(Cafe cafe, String name, int price, String imageUrl) {
        this.cafe = cafe;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void changeCafeMenuName(String name) {
        this.name = name;
    }

    public void changeCafeMenuPrice(int price) {
        this.price = price;
    }

    public void changeCafeMenuImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
