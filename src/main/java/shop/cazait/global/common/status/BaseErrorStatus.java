package shop.cazait.global.common.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BaseErrorStatus {

    /**
     * Request 관련 공통 오류
     */
    SUCCESS("SUCCESS", "요청에 성공하였습니다."),
    EMPTY_JWT("FAIL","JWT를 입력해주세요."),
    INVALID_JWT("FAIL", "유효하지 않은 JWT입니다.");

    private final String result;
    private final String message;

}
