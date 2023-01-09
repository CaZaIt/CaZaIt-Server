package shop.cazait.global.error;

import lombok.AllArgsConstructor;
import shop.cazait.global.common.status.BaseResponseStatus;

@AllArgsConstructor
public class BaseException extends Exception{

    private BaseResponseStatus error;

}
