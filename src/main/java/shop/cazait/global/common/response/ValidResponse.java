package shop.cazait.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@Getter
@JsonPropertyOrder({"result", "message", "errorField"})
public class ValidResponse<T> {

    private String result;
    private String message;
    private T errorField;

    public ValidResponse(ErrorStatus status, T errorField) {
        this.result = status.getResult();
        this.message = status.getMessage();
        this.errorField = errorField;
    }

}
