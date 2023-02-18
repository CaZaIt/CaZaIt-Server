package shop.cazait.domain.review.requestvalue;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;



@Getter
@AllArgsConstructor
public enum SortType {
    NEWEST("newest", "id", Sort.Direction.DESC),
    OLDEST("oldest", "id", Sort.Direction.ASC);

    private final String value;
    private final String property;  // Sort.by()로 생성 시, column명이 아닌 엔티티의 property명이 기준임
    private final Sort.Direction direction;

    public static final Map<String, SortType> map = new HashMap<>();

    static {
        for (SortType sortType : SortType.values()) {
            map.put(sortType.value, sortType);
        }
    }

    // 존재하지 않는 value일 시 NullPointerException 발생
    public static SortType of(String value) {
        try {
            return map.get(value);
        } catch (NullPointerException nullPointerException) {
            return SortType.NEWEST;
        }
    }
}
