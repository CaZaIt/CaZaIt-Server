package shop.cazait.global.error.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessStatus {

    /**
     * Success Code
     * 200 : 요청 성공
     * 201 : 요청으로 인해 새로운 리소스 생성
     * 204 : 요청에 성공했으나 데이터는 없음
     */

    /**
     * Success Code : 200
     * OK
     */
    SUCCESS(200,"SUCCESS","요청이 완료 되었습니다."),
    SIGNUP_AVAILABLE(200,"SUCCESS","회원가입이 가능합니다"),

    /**
     * Success Code : 201
     * Created
     */
    CREATE_CAFE_IMAGE(201, "SUCCESS", "카페 이미지 등록이 완료 되었습니다"),
    CREATE_FAVORITES(201, "SUCCESS", "즐겨찾기 등록이 완료 되었습니다"),
    CREATE_CONGESTION(201, "SUCCESS", "혼잡도 등록이 완료 되었습니다"),
    CREATE_MENU(201, "SUCCESS", "메뉴 등록이 완료 되었습니다"),
    CREATE_CAFE(201, "SUCCESS", "카페 등록이 완료 되었습니다"),
    CREATE_REVIEW(201, "SUCCESS", "리뷰 등록이 완료 되었습니다"),
    CREATE_USER(201, "SUCCESS", "유저 등록이 완료 되었습니다"),
    CREATE_MASTER(201, "SUCCESS", "마스터 등록이 완료 되었습니다"),

    /**
     * Success Code : 204
     * No Content
     */
    NO_CONTENT_SUCCESS(204, "SUCCESS", "요청에 대한 정보가 없습니다.");

    private final int code;
    private final String result;
    private final String message;
}
