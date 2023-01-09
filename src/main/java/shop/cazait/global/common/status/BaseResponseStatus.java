package shop.cazait.global.common.status;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * Request 관련 공통 오류
     */
    SUCCESS(true, "요청에 성공하였습니다."),

    EMPTY_JWT(false,"JWT를 입력해주세요."),
    INVALID_JWT(false, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,"권한이 없는 유저의 접근입니다.");

    private final boolean status;
    private final String message;

    private BaseResponseStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

}
