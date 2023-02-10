package shop.cazait.global.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.SuccessStatus;


@Getter
@JsonPropertyOrder({"code", "result", "message", "data"})
public class SuccessResponse<T> {

    private int code;
    private String result;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public SuccessResponse(SuccessStatus status, T data) {
        this.code = status.getCode();
        this.result = status.getResult();
        this.message = status.getMessage();
        this.data = data;
    }

}
