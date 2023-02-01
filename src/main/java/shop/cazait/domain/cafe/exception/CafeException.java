package shop.cazait.domain.cafe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class CafeException extends RuntimeException {
    private ErrorStatus error;
}