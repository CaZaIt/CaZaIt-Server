package shop.cazait.domain.congestion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class CongestionException extends BaseException {
    public CongestionException(ErrorStatus error) {
        super(error);
    }

}
