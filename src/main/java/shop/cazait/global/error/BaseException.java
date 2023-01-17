package shop.cazait.global.error;

import lombok.AllArgsConstructor;
import shop.cazait.global.common.status.BaseErrorStatus;

@AllArgsConstructor
public class BaseException extends Exception{

    private BaseErrorStatus error;

}
