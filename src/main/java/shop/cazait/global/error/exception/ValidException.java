package shop.cazait.global.error.exception;

import lombok.Getter;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
public class ValidException extends MethodArgumentNotValidException {

    private ErrorStatus error;
    private StringBuilder description = new StringBuilder();

    protected ValidException(MethodParameter parameter, BindingResult bindingResult) {

        super(parameter, bindingResult);
        this.error = ErrorStatus.INVALID_REQUEST;

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            description.append("[");
            description.append(fieldError.getField());
            description.append("](은)는");
            description.append(fieldError.getDefaultMessage());
            description.append(" 입력된 값: [");
            description.append(fieldError.getRejectedValue());
            description.append("]\n");
        }

    }

}
