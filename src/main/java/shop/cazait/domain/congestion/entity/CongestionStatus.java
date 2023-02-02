package shop.cazait.domain.congestion.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CongestionStatus {

    // 미등록, 종료, 여유, 보통, 혼잡, 매우 혼잡
    NONE("none", 0),
    CLOSE("close", -1),
    FREE("free", 4),
    NORMAL("normal", 3),
    CROWDED("crowded", 2),
    VERY_CROWDED("very_crowded", 1);

    private final String value;
    private final int level;

}
