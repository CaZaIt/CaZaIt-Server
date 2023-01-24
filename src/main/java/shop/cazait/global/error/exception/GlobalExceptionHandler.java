package shop.cazait.global.error.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.congestion.exception.CongestionException;
import shop.cazait.global.common.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BaseException.class })
    protected FailResponse handleBaseException(BaseException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ FavoritesException.class })
    protected FailResponse handleFavoritesException(FavoritesException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ CongestionException.class })
    protected FailResponse handleCongestionException(CongestionException exception) {
        return new FailResponse(exception.getError());
    }
    
}
