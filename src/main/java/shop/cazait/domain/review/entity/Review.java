package shop.cazait.domain.review.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


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

    @NonNull
    private long userId;

    @NonNull
    private long cafeId;

    @NonNull
    private int score;

    @Nullable
    private String content;

    /**
     * 추후 확장
     *@NonNull
     *private int seat;
     *@NonNull
     *private int cleanliness;
     */
}
