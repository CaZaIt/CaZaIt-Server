package shop.cazait.domain.cafe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class CafeException extends BaseException {

    public CafeException(ErrorStatus error) {
        super(error);
    }

}