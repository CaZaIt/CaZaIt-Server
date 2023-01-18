package shop.cazait.domain.favorites.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FavoritesErrorStatus {

    INVALID_CAFE_FAVORITES("FAIL", "유효하지 않은 즐겨찾기 입니다.");

    private final String result;
    private final String message;

}
