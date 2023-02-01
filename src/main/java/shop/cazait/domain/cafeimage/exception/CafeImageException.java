package shop.cazait.domain.cafeimage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class CafeImageException extends RuntimeException {
    private ErrorStatus error;
}