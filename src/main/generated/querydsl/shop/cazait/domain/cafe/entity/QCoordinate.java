package shop.cazait.domain.cafe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoordinate is a Querydsl query type for Coordinate
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCoordinate extends BeanPath<Coordinate> {

    private static final long serialVersionUID = 611749206L;

    public static final QCoordinate coordinate = new QCoordinate("coordinate");

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public QCoordinate(String variable) {
        super(Coordinate.class, forVariable(variable));
    }

    public QCoordinate(Path<? extends Coordinate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoordinate(PathMetadata metadata) {
        super(Coordinate.class, metadata);
    }

}

