package shop.cazait.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.cazait.global.error.status.ErrorStatus;

@Data
@AllArgsConstructor
public class BaseException extends Exception{

    private ErrorStatus error;

}
