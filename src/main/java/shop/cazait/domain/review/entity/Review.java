package shop.cazait.domain.review.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.review.dto.ReviewUpdateInDTO;
import shop.cazait.domain.user.entity.User;
import shop.cazait.global.common.entity.BaseEntity;



@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private Integer score;

    private String content;


    @Builder
    public Review(User user, Cafe cafe, Integer score, String content) {
        this.user = user;
        this.cafe = cafe;
        this.score = score;
        this.content = content;
    }

    public Review update(ReviewUpdateInDTO patchReviewReq) {
        this.score = patchReviewReq.getScore();
        this.content = patchReviewReq.getContent();

        return this;
    }

    /**
     * 추후 확장
     *@NonNull
     *private Integer seat;
     *@NonNull
     *private Integer cleanliness;
     */
}
