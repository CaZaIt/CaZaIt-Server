package shop.cazait.domain.cafemenu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCafeMenu is a Querydsl query type for CafeMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCafeMenu extends EntityPathBase<CafeMenu> {

    private static final long serialVersionUID = -1842924839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCafeMenu cafeMenu = new QCafeMenu("cafeMenu");

    public final shop.cazait.domain.cafe.model.entity.QCafe cafe;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QCafeMenu(String variable) {
        this(CafeMenu.class, forVariable(variable), INITS);
    }

    public QCafeMenu(Path<? extends CafeMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCafeMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCafeMenu(PathMetadata metadata, PathInits inits) {
        this(CafeMenu.class, metadata, inits);
    }

    public QCafeMenu(Class<? extends CafeMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.model.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
    }

}

