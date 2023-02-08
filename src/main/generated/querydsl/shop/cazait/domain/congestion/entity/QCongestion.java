package shop.cazait.domain.congestion.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCongestion is a Querydsl query type for Congestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCongestion extends EntityPathBase<Congestion> {

    private static final long serialVersionUID = -1291795185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCongestion congestion = new QCongestion("congestion");

    public final shop.cazait.global.common.entity.QBaseEntity _super = new shop.cazait.global.common.entity.QBaseEntity(this);

    public final shop.cazait.domain.cafe.entity.QCafe cafe;

    public final EnumPath<CongestionStatus> congestionStatus = createEnum("congestionStatus", CongestionStatus.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final EnumPath<shop.cazait.global.common.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QCongestion(String variable) {
        this(Congestion.class, forVariable(variable), INITS);
    }

    public QCongestion(Path<? extends Congestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCongestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCongestion(PathMetadata metadata, PathInits inits) {
        this(Congestion.class, metadata, inits);
    }

    public QCongestion(Class<? extends Congestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
    }

}

