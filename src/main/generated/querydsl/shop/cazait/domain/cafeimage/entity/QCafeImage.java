package shop.cazait.domain.cafeimage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCafeImage is a Querydsl query type for CafeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCafeImage extends EntityPathBase<CafeImage> {

    private static final long serialVersionUID = -1557290937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCafeImage cafeImage = new QCafeImage("cafeImage");

    public final shop.cazait.domain.cafe.model.entity.QCafe cafe;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public QCafeImage(String variable) {
        this(CafeImage.class, forVariable(variable), INITS);
    }

    public QCafeImage(Path<? extends CafeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCafeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCafeImage(PathMetadata metadata, PathInits inits) {
        this(CafeImage.class, metadata, inits);
    }

    public QCafeImage(Class<? extends CafeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.model.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
    }

}

