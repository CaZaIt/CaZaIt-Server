package shop.cazait.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

import static shop.cazait.global.error.status.ErrorStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"result", "message", "data"})
public class BaseResponse<T> {

    private String result;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse(T data) {
        this.result = SUCCESS.getResult();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }

    public BaseResponse(ErrorStatus status) {
        this.result = status.getResult();
        this.message = status.getMessage();
    }

}
