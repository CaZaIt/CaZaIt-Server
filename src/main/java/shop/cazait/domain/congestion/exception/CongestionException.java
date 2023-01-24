package shop.cazait.domain.congestion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class CongestionException extends RuntimeException {

    private final ErrorStatus error;

}
