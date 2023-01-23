package shop.cazait.domain.master.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MasterException extends Exception {
	private MasterErrorStatus error;  //BaseResonseStatus 객체에 매핑
}