package shop.cazait.domain.congestion.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CongestionStatus {

    // 미등록, 종료, 여유, 보통, 혼잡, 매우 혼잡
    NONE("NONE", 0),
    CLOSE("CLOSE", -1),
    FREE("FREE", 4),
    NORMAL("NORMAL", 3),
    CROWDED("CROWDED", 2),
    VERY_CROWDED("VERY_CROWDED", 1);

    private final String value;
    private final int level;

}
