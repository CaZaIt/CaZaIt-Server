package shop.cazait.domain.review.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReviewErrorStatus {
    INVALID_REVIEW_ID("FAIL", "유효하지 않은 리뷰 ID입니다.");

    private final String result;
    private final String message;
}
