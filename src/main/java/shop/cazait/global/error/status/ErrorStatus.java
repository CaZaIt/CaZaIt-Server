package shop.cazait.global.error.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    /**
     *  Error Code
     *  400 : 잘못된 요청
     *  401 : JWT에 대한 오류
     *  403 : 요청한 정보에 대한 권한 없음.
     *  404 : 존재하지 않는 정보에 대한 요청.
     */

    /**
     * Success Code : 400
     * Bad Request
     */
    INVALID_REQUEST(400, "FAIL", "유효하지 않은 요청입니다."),
    INVALID_CONGESTION(400, "FAIL", "유효하지 않은 혼잡도 입니다."),
    FAILED_TO_LOGIN(400, "FAIL", "아이디 또는 비밀번호를 잘못 입력했습니다."),
    ALREADY_INACTIVE_MASTER(400, "FAIL", "이미 탈퇴한 계정입니다"),
    NOT_EXPIRED_TOKEN(400 ,"FAIL", "JWT가 아직 만료되지 않아, 재발급이 불가능합니다."),
    EXIST_ACCOUNTNAME(400,"FAIL","이미 존재하는 아이디입니다."),
    EXIST_PHONENUMBER(400,"FAIL","이미 가입된 전화번호입니다"),
    EXIST_NICKNAME(400,"FAIL","이미 존재하는 닉네임입니다."),
    INVALID_ROLE(400,"FAIL","유효하지 않은 역할입니다."),
    FAIL_UPLOAD_IMAGE(400, "FAIL", "이미지 업로드를 실패했습니다."),
    EXIST_FAVORITES(400, "FAIL", "이미 존재하는 즐겨찾기입니다."),
    INVALID_VERIFICATION_CODE(400,"FAIL","인증번호가 올바르지 않습니다."),
    EXPIRED_VERIFICATION_CODE(400,"FAIL","만료된 인증번호입니다."),
    INVALID_PHONENUMBER(400,"FAIL","잘못된 전화번호입니다"),
    INVALID_USER_INFO(400,"FAIL","인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 정보가 회원정보와 일치하는지 확인해주세요."),
    INVALID_USER_PASSWORD(400,"FAIL","비밀번호가 올바르지 않습니다."),

    /**
     * Success Code : 401
     * Unauthorized
     */
    EMPTY_JWT(401, "FAIL","JWT를 입력해주세요."),
    INVALID_JWT(401, "FAIL", "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(401, "FAIL", "만료된 JWT입니다."),

    /**
     * Success Code : 403
     * Forbidden
     */
    NOT_OPERATE_CAFE(403, "FAIL", "현재 접속한 MASTER 계정이 운영하는 카페가 아닙니다."),


    /**
     * Success Code : 404
     * Not Found
     */
    NOT_EXIST_USER(404,"FAIL", "존재하지 않는 유저입니다."),
    NOT_EXIST_MASTER(404, "FAIL", "존재하지 않는 마스터입니다."),
    NOT_EXIST_CAFE(404,"FAIL", "존재하지 않는 카페입니다."),
    NOT_EXIST_MENU(404,"FAIL", "존재하지 않는 메뉴입니다."),
    NOT_EXIST_FAVORITES(404,"FAIL", "존재하지 않는 즐겨찾기 입니다."),
    NOT_EXIST_REVIEW(404,"FAIL", "존재하지 않는 리뷰 ID입니다."),
    NOT_EXIST_IMAGE(404,"FAIL", "존재하지 않는 이미지입니다."),
    PAGE_NOT_FOUND(404,"FAIL","페이지를 찾을 수 없습니다"),

    /**
     * Cafe Error
     */
    INVALID_CAFE_NAME(404,"FAIL", "유효하지 않은 CAFE NAME입니다."),

    /**
     * Master Error
     */
    // todo : 중복 이메일 통합하기
    DUPLICATE_USER_LOGIN_EMAIL(400,"FAIL","중복된 마스터 계정의 이메일입니다.");

    private final int code;
    private final String result;
    private final String message;

}
