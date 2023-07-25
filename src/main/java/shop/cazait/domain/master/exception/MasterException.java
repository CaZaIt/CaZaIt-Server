package shop.cazait.domain.master.exception;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

import lombok.Getter;

@Getter
public class MasterException extends BaseException {

	public MasterException(ErrorStatus error) {
		super(error);
	}

}
