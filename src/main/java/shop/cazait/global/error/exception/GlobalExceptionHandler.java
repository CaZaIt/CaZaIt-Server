package shop.cazait.global.error.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.favorites.exception.FavoritesException;
import shop.cazait.domain.congestion.exception.CongestionException;
import shop.cazait.domain.master.error.MasterException;
import shop.cazait.domain.review.exception.ReviewException;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.FailResponse;
import shop.cazait.global.error.status.ErrorStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BaseException.class })
    protected FailResponse handleBaseException(BaseException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ CafeException.class })
    protected FailResponse handleCongestionException(CafeException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ CafeImageException.class })
    protected FailResponse handleCongestionException(CafeImageException exception) {
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

    @ExceptionHandler({ MasterException.class })
    protected FailResponse handleCongestionException(MasterException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ UserException.class })
    protected FailResponse handleUserException(UserException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ReviewException.class })
    protected FailResponse handlerReviewException(ReviewException exception) {
        return new FailResponse(exception.getError());
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected FailResponse handleValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder description = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            description.append("[");
            description.append(fieldError.getField());
            description.append("](은)는");
            description.append(fieldError.getDefaultMessage());
            description.append(" 입력된 값: [");
            description.append(fieldError.getRejectedValue());
            description.append("]\n");
        }
        return new FailResponse(ErrorStatus.INVALID_REQUEST, description.toString());

    }

}
