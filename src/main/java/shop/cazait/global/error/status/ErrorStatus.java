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
    SUCCESS("SUCCESS", "요청에 성공하였습니다."),
    EMPTY_JWT("FAIL","JWT를 입력해주세요."),
    INVALID_JWT("FAIL", "유효하지 않은 JWT입니다.");

    /**
     * 제목 양식 : Entity Error
     */
    // 아래에 해당하는 Entity에 대한 Error Code 작성


    private final String result;
    private final String message;

}
