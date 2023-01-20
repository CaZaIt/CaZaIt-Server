package shop.cazait.global.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.cazait.global.common.status.BaseErrorStatus;

@Data
@AllArgsConstructor
public class BaseException extends Exception{

    private BaseErrorStatus error;

}
