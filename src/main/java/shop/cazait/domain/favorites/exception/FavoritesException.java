package shop.cazait.domain.favorites.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class FavoritesException extends BaseException {

    public FavoritesException(ErrorStatus error) {
        super(error);
    }

}
