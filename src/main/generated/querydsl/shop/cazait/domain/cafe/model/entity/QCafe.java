package shop.cazait.domain.cafe.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCafe is a Querydsl query type for Cafe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCafe extends EntityPathBase<Cafe> {

    private static final long serialVersionUID = -1227050378L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCafe cafe = new QCafe("cafe");

    public final shop.cazait.global.common.entity.QBaseEntity _super = new shop.cazait.global.common.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final ListPath<shop.cazait.domain.cafeimage.entity.CafeImage, shop.cazait.domain.cafeimage.entity.QCafeImage> cafeImage = this.<shop.cazait.domain.cafeimage.entity.CafeImage, shop.cazait.domain.cafeimage.entity.QCafeImage>createList("cafeImage", shop.cazait.domain.cafeimage.entity.CafeImage.class, shop.cazait.domain.cafeimage.entity.QCafeImage.class, PathInits.DIRECT2);

    public final shop.cazait.domain.congestion.entity.QCongestion congestion;

    public final shop.cazait.domain.coordinate.entity.QCoordinate coordinate;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.cazait.domain.master.model.entity.QMaster master;

    public final StringPath name = createString("name");

    //inherited
    public final EnumPath<shop.cazait.global.common.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QCafe(String variable) {
        this(Cafe.class, forVariable(variable), INITS);
    }

    public QCafe(Path<? extends Cafe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCafe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCafe(PathMetadata metadata, PathInits inits) {
        this(Cafe.class, metadata, inits);
    }

    public QCafe(Class<? extends Cafe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.congestion = inits.isInitialized("congestion") ? new shop.cazait.domain.congestion.entity.QCongestion(forProperty("congestion"), inits.get("congestion")) : null;
        this.coordinate = inits.isInitialized("coordinate") ? new shop.cazait.domain.coordinate.entity.QCoordinate(forProperty("coordinate")) : null;
        this.master = inits.isInitialized("master") ? new shop.cazait.domain.master.model.entity.QMaster(forProperty("master")) : null;
    }

}

