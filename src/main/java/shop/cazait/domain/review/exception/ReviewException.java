package shop.cazait.domain.review.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.cazait.global.common.response.FailResponse;
import shop.cazait.global.error.status.ErrorStatus;



@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewException extends RuntimeException{
    private final ErrorStatus error;
}
