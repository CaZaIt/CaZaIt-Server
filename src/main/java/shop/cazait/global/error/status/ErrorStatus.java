package shop.cazait.global.error.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    /**
     * 공통 Error Code
     */
    EMPTY_JWT("FAIL","JWT를 입력해주세요."),
    INVALID_JWT("FAIL", "유효하지 않은 JWT입니다."),

    /**
     * 제목 양식 : Entity Error
     */

    /**
     * Cafe Error
     */
    NOT_EXIST_CAFE("SUCCESS", "카페가 존재하지 않습니다."),
    INVALID_CAFE_ID("FAIL", "유효하지 않은 CAFE ID입니다."),
    INVALID_CAFE_NAME("FAIL", "유효하지 않은 CAFE NAME입니다."),

    /**
     * Cafe Image Error
     */
    INVALID_CAFE_IMAGE_ID("FAIL", "유효하지 않은 CAFE IMAGE ID입니다.");


    private final String result;
    private final String message;

}
