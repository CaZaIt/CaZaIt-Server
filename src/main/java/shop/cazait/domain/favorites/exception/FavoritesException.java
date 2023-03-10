package shop.cazait.domain.favorites.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class FavoritesException extends RuntimeException {

    private ErrorStatus error;

}
