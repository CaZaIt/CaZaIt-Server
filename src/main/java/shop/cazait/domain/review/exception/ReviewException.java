package shop.cazait.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@RequiredArgsConstructor
public class ReviewException extends RuntimeException{
    private final ErrorStatus error;
}
