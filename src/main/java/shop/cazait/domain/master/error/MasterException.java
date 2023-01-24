package shop.cazait.domain.master.error;
import shop.cazait.global.error.status.ErrorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MasterException extends Exception {
	private ErrorStatus error;  //BaseResonseStatus 객체에 매핑
}
