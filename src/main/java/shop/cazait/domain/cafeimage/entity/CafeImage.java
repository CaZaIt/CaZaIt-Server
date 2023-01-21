package shop.cazait.domain.cafeimage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CafeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public CafeImage(Cafe cafe, String imageUrl) {
        this.cafe = cafe;
        this.imageUrl = imageUrl;
    }
}
