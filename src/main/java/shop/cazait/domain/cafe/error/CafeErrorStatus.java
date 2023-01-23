package shop.cazait.domain.cafe.error;

import lombok.Getter;

@Getter
public enum CafeErrorStatus {

    NON_EXIST_CAFE(false, "CAFE가 존재하지 않습니다.");

    private final boolean status;
    private final String message;

    private CafeErrorStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
