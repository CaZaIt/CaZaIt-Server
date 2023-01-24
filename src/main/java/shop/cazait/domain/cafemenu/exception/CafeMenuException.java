package shop.cazait.domain.cafemenu.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CafeMenuException extends RuntimeException {

    private final CafeMenuErrorStatus error;

}
