package shop.cazait.global.pagination;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaginationCollection<T> {

    private final List<T> contentsWithNextCursor; // 현재 스크롤의 요소 + 다음 스크롤의 요소 1개 (다음 스크롤이 있는지 확인을 위한)
    private final int countPerScroll;

    public static <T> ScrollPaginationCollection<T> of(List<T> contentsWithNextCursor, int size) {
        return new ScrollPaginationCollection<>(contentsWithNextCursor, size);
    }

    public boolean isLastScroll() {
        return this.contentsWithNextCursor.size() <= countPerScroll;
    }

    public List<T> getCurrentScrollItems() {
        if (isLastScroll()) {
            return this.contentsWithNextCursor;
        }
        return this.contentsWithNextCursor.subList(0, countPerScroll);
    }

    public T getNextCursor() {
        return contentsWithNextCursor.get(countPerScroll - 1);
    }

}