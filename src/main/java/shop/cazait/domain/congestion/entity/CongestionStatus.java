package shop.cazait.domain.congestion.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CongestionStatus {

    // 미등록, 종료, 여유, 보통, 혼잡, 매우 혼잡
    NONE("none"), CLOSE("close"), FREE("free"), NORMAL("normal"), CROWDED("crowded"), VERY_CROWDED("very_crowded");

    private final String value;

}
