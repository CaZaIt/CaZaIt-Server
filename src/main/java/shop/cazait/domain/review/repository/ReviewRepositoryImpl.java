package shop.cazait.domain.review.repository;


import static shop.cazait.domain.review.entity.QReview.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.review.entity.Review;



@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override   // 최신 순 정렬 (ID가 작은 순서 -> 큰 순으로 진행)
    public List<Review> findNewestPageByCafeId(Long cafeId, Integer score, Long lastId, int size) {
        List<Review> reviews = jpaQueryFactory.selectFrom(review)
                .where(scoreExist(score),
                        review.id.gt(lastId),
                        review.cafe.id.eq(cafeId))
                .orderBy(review.id.asc())
                .limit(size + 1)
                .fetch();

        return reviews;
    }

    @Override   // 오래된 순 정렬 (ID가 큰 순서 -> 작은 순으로 진행)
    public List<Review> findOldestPageByCafeId(Long cafeId, Integer score, Long lastId, int size) {
        List<Review> reviews = jpaQueryFactory.selectFrom(review)
                .where(scoreExist(score),
                        review.id.lt(lastId),
                        review.cafe.id.eq(cafeId))
                .orderBy(review.id.desc())
                .limit(size + 1)
                .fetch();

        return reviews;
    }

    private BooleanExpression scoreExist(Integer score) {
        return score != null ? review.score.eq(score) : null;
    }
}
