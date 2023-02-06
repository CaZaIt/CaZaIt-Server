package shop.cazait.domain.checklog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckLog is a Querydsl query type for CheckLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckLog extends EntityPathBase<CheckLog> {

    private static final long serialVersionUID = -1778427879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckLog checkLog = new QCheckLog("checkLog");

    public final shop.cazait.domain.cafe.entity.QCafe cafe;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final shop.cazait.domain.user.entity.QUser user;

    public QCheckLog(String variable) {
        this(CheckLog.class, forVariable(variable), INITS);
    }

    public QCheckLog(Path<? extends CheckLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckLog(PathMetadata metadata, PathInits inits) {
        this(CheckLog.class, metadata, inits);
    }

    public QCheckLog(Class<? extends CheckLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafe = inits.isInitialized("cafe") ? new shop.cazait.domain.cafe.entity.QCafe(forProperty("cafe"), inits.get("cafe")) : null;
        this.user = inits.isInitialized("user") ? new shop.cazait.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

