package shop.cazait.domain.cafe.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CafeException extends Exception{

    private CafeErrorStatus cafeErrorStatus;

}
