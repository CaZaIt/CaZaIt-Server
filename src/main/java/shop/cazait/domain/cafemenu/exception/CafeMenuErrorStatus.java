package shop.cazait.domain.cafemenu.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CafeMenuErrorStatus {

    NOT_REGISTER_MENU("FAIL", "메뉴 등록이 필요합니다."),
    INVALID_MENU("FAIL", "유효하지 않은 메뉴입니다.");

    private final String result;
    private final String message;

}
