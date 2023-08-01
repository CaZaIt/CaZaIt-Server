package shop.cazait.domain.menu.exception;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

public class MenuException extends BaseException {

    public MenuException(ErrorStatus error) {
        super(error);
    }

}
