package shop.cazait.domain.cafe.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.cazait.global.error.status.ErrorStatus;

@Data
@AllArgsConstructor
public class CafeException extends RuntimeException {
    private ErrorStatus error;
}