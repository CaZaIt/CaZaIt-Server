package shop.cazait.domain.favorites.exception;

public enum FavoritesErrorStatus {

    INVALID_CAFE_FAVORITES(false, "유효하지 않은 즐겨찾기 입니다.");

    private final boolean status;
    private final String message;

    private FavoritesErrorStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
