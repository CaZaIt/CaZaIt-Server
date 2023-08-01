package shop.cazait.domain.master.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class MasterException extends BaseException {

	public MasterException(ErrorStatus error) {
		super(error);
	}

}
 