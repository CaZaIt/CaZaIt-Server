package shop.cazait.domain.menu.exception;

import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class MenuException extends BaseException {

    public MenuException(ErrorStatus error) {
        super(error);
    }

}
