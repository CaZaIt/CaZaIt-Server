package shop.cazait.domain.review.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.user.entity.User;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    @Column(nullable = false)
    private Cafe cafe;

    @Column(nullable = false)
    private int score;

    private String content;

    /**
     * 추후 확장
     *@NonNull
     *private int seat;
     *@NonNull
     *private int cleanliness;
     */
}
