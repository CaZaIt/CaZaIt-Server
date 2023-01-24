package shop.cazait.domain.cafemenu.exception;

import lombok.RequiredArgsConstructor;
import shop.cazait.global.error.status.ErrorStatus;

@RequiredArgsConstructor
public class CafeMenuException extends RuntimeException {

    private final ErrorStatus error;

}
