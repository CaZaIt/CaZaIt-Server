package shop.cazait.domain.master.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMaster is a Querydsl query type for Master
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaster extends EntityPathBase<Master> {

    private static final long serialVersionUID = -182342587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaster master = new QMaster("master");

    public final shop.cazait.global.common.entity.QBaseEntity _super = new shop.cazait.global.common.entity.QBaseEntity(this);

    public final StringPath accountName = createString("accountName");

    public final shop.cazait.domain.cafe.entity.QCafe cafe;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final EnumPath<shop.cazait.global.common.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QMaster(String variable) {
        this(Master.class, forVariable(variable), INITS);
    }

    public QMaster(Path<? extends Master> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMaster(PathMetadata metadata, PathInits inits) {
        this(Master.class, metadata, inits);
    }

    public QMaster(Class<? extends Master> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
    }

}

