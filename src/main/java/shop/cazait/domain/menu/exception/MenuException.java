package shop.cazait.domain.menu.exception;

import lombok.RequiredArgsConstructor;
import shop.cazait.global.error.status.ErrorStatus;

@RequiredArgsConstructor
public class MenuException extends RuntimeException {

    private final ErrorStatus error;

}
