package shop.cazait.domain.congestion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CongestionException extends RuntimeException {

    private final CongestionErrorStatus error;

}
