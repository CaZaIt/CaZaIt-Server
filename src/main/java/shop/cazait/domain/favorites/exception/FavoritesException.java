package shop.cazait.domain.favorites.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoritesException extends IllegalArgumentException {

    private FavoritesErrorStatus error;

}
