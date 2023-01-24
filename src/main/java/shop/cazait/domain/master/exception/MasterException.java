package shop.cazait.domain.master.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class MasterException {

    private ErrorStatus error;

}
