package shop.cazait.domain.master.error;

import lombok.Getter;
/**
 * 에러 코드 관리
 * */

@Getter
public enum MasterResStatus {

    DUPLICATE_USER_LOGIN_EMAIL(false, "중복된 마스터 계정의 이메일입니다."),

    ALREADY_INACTIVE_MASTER(true,"이미 탈퇴한 계정입니다"),
    NOT_EXISTS_MASTER(false, "없는 마스터 계정입니다.");

    private final boolean isSuccess;
    private final String message;

    private MasterResStatus(boolean isSuccess, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
