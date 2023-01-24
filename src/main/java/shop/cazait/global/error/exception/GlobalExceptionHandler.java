package shop.cazait.global.error.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.congestion.exception.CongestionException;
import shop.cazait.global.common.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BaseException.class })
    protected BaseResponse handleBaseException(BaseException exception) {
        return new BaseResponse(exception.getError());
    }

    @ExceptionHandler({ FavoritesException.class })
    protected BaseResponse handleFavoritesException(FavoritesException exception) {
        return new BaseResponse(exception.getError());
    }

    @ExceptionHandler({ CongestionException.class })
    protected BaseResponse handleCongestionException(CongestionException exception) {
        return new BaseResponse(exception.getError());
    }
    
}
