package shop.cazait.domain.review.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.cazait.global.common.response.FailResponse;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewException extends RuntimeException{
    private final ReviewErrorStatus error;
}
