package shop.cazait.domain.auth;

import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.error.status.ErrorStatus;

public enum Role {
    USER, MASTER;


    public static Role of(String queryString) throws UserException {
        if (queryString.equals("user")) {
            return USER;
        } else if (queryString.equals("master")) {
            return MASTER;
        }
        throw new UserException(ErrorStatus.INVALID_ROLE);
    }
}
