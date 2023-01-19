package shop.cazait.domain.review.requestvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SortType {
    NEWEST("newest", "created_at", Sort.Direction.DESC),
    OLDEST("oldest", "created_at", Sort.Direction.ASC),
    POPULARITY("popularity", "score", Sort.Direction.DESC);

    private final String value;
    private final String column;
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
            return NEWEST;
        }
    }
}
