package shop.cazait.domain.cafeimage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class CafeImageException extends BaseException {

    public CafeImageException(ErrorStatus error) {
        super(error);
    }

}