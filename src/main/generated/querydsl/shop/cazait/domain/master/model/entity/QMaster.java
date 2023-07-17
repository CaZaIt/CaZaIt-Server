package shop.cazait.domain.master.model.entity;

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

    private static final long serialVersionUID = -940424128L;

    public static final QMaster master = new QMaster("master");

    public final shop.cazait.global.common.entity.QBaseEntity _super = new shop.cazait.global.common.entity.QBaseEntity(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final ListPath<shop.cazait.domain.cafe.model.entity.Cafe, shop.cazait.domain.cafe.model.entity.QCafe> cafes = this.<shop.cazait.domain.cafe.model.entity.Cafe, shop.cazait.domain.cafe.model.entity.QCafe>createList("cafes", shop.cazait.domain.cafe.model.entity.Cafe.class, shop.cazait.domain.cafe.model.entity.QCafe.class, PathInits.DIRECT2);

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
        super(Master.class, forVariable(variable));
    }

    public QMaster(Path<? extends Master> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaster(PathMetadata metadata) {
        super(Master.class, metadata);
    }

}

