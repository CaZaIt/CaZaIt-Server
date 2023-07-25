package shop.cazait.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class BaseException extends RuntimeException {

    private ErrorStatus error;

    public BaseException(ErrorStatus error) {
        this.error = error;
    }

}
