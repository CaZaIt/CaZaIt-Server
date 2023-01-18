package shop.cazait.domain.favorites.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FavoritesException extends IllegalArgumentException {

    private FavoritesErrorStatus errorStatus;

}
