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
    INVALID_REQUEST("FAIL", "유효하지 않은 Request입니다."),

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
    INVALID_CAFE_IMAGE_ID("FAIL", "유효하지 않은 CAFE IMAGE ID입니다."),

    /**
     * Cafe Menu Error
     */
    NOT_REGISTER_MENU("FAIL", "메뉴 등록이 필요합니다."),
    INVALID_MENU("FAIL", "유효하지 않은 메뉴입니다."),

    /**
     * Congestion Error
     */
    CONGESTION_STATUS_EMPTY("FAIL", "혼잡도를 입력해 주세요."),
    INVALID_CONGESTION_STATUS("FAIL", "유효하지 않은 혼잡도입니다."),

    /**
     * Favorites Error
     */
    INVALID_CAFE_FAVORITES("FAIL", "유효하지 않은 즐겨찾기 입니다."),

    /**
     * Master Error
     */
    DUPLICATE_USER_LOGIN_EMAIL("FAIL","중복된 마스터 계정의 이메일입니다."),
    ALREADY_INACTIVE_MASTER("FAIL", "이미 탈퇴한 계정입니다"),
    NOT_EXIST_MASTER("FAIL", "없는 마스터 계정입니다."),

    /**
     * Review Error
     */
    INVALID_REVIEW_ID("FAIL", "유효하지 않은 리뷰 ID입니다."),

    /**
     * User Error
     */
    EMPTY_EMAIL("FAIL", "이메일을 입력해주세요."),
    EMPTY_PASSWORD("FAIL","비밀번호를 입력해주세요"),
    EMPTY_NICKNAME("FAIL", "닉네임을 입력해주세요."),

    INVALID_EMAIL("FAIL", "이메일 형식을 확인해주세요."),
    INVALID_PASSWORD("FAIL", "비밀번호 형식을 확인해주세요."),

    EXIST_EMAIL("FAIL","이미 존재하는 이메일입니다."),
    EXIST_NICKNAME("FAIL","이미 존재하는 닉네임입니다."),

    NOT_EXIST_USER("FAIL", "유저가 존재하지 않습니다."),

    FAILED_TO_LOGIN("FAIL","아이디 또는 비밀번호를 잘못 입력했습니다.");


    private final String result;
    private final String message;

}
