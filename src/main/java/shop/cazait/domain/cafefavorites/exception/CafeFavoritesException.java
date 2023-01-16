package shop.cazait.domain.cafefavorites.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CafeFavoritesException extends IllegalArgumentException {

    private CafeFavoritesErrorStatus errorStatus;

}
