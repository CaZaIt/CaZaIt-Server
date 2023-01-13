package shop.cazait.domain.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.user.entity.User;



@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private int score;

    private String content;

    @Builder
    public Review(User user, Cafe cafe, int score, String content) {
        this.user = user;
        this.cafe = cafe;
        this.score = score;
        this.content = content;
    }

    /**
     * 추후 확장
     *@NonNull
     *private int seat;
     *@NonNull
     *private int cleanliness;
     */
}
