package shop.cazait.domain.cafefavorites.exception;

public enum CafeFavoritesErrorStatus {

    INVALID_CAFE_FAVORITES(false, "유효하지 않은 즐겨찾기 입니다.");

    private final boolean status;
    private final String message;

    private CafeFavoritesErrorStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
