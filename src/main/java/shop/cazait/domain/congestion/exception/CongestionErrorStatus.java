package shop.cazait.domain.congestion.exception;

public enum CongestionErrorStatus {

    CONGESTION_STATUS_EMPTY("FAIL", "혼잡도를 입력해 주세요."),
    INVALID_CONGESTION_STATUS("FAIL", "유효하지 않은 혼잡도 입니다.");

    private final String result;
    private final String message;

    private CongestionErrorStatus(String result, String message) {
        this.result = result;
        this.message = message;
    }
}
