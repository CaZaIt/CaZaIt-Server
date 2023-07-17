package shop.cazait.domain.favorites.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorites is a Querydsl query type for Favorites
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorites extends EntityPathBase<Favorites> {

    private static final long serialVersionUID = 1262252423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorites favorites = new QFavorites("favorites");

    public final shop.cazait.domain.cafe.model.entity.QCafe cafe;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.cazait.domain.user.entity.QUser user;

    public QFavorites(String variable) {
        this(Favorites.class, forVariable(variable), INITS);
    }

    public QFavorites(Path<? extends Favorites> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorites(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorites(PathMetadata metadata, PathInits inits) {
        this(Favorites.class, metadata, inits);
    }

    public QFavorites(Class<? extends Favorites> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.model.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
        this.user = inits.isInitialized("user") ? new shop.cazait.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

