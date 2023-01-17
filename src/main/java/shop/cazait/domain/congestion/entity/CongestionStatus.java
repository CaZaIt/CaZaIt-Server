package shop.cazait.domain.congestion.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CongestionStatus {

    // 여유, 보통, 혼잡, 매우 혼잡
    FREE("free"), NORMAL("normal"), CROWDED("crowded"), VERY_CROWDED("very_crowded");

    private final String value;

}
