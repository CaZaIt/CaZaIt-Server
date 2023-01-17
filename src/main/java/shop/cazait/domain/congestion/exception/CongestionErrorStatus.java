package shop.cazait.domain.congestion.exception;

public enum CongestionErrorStatus {

    CONGESTION_STATUS_EMPTY(false, "혼잡도 상태를 입력해 주세요.");

    private final boolean status;
    private final String message;

    private CongestionErrorStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
