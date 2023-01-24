package shop.cazait.domain.cafeimage.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.cazait.global.error.status.ErrorStatus;

@Data
@AllArgsConstructor
public class CafeImageException extends RuntimeException {
    private ErrorStatus error;
}