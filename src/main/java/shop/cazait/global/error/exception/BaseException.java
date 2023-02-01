package shop.cazait.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class BaseException extends Exception{

    private ErrorStatus error;

}
