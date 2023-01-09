package shop.cazait.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.common.status.BaseResponseStatus;

import static shop.cazait.global.common.status.BaseResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"status", "message", "data"})
public class BaseResponse<T> {

    private Boolean status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse(T data) {
        this.status = SUCCESS.isStatus();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }

    public BaseResponse(BaseResponseStatus status) {
        this.status = status.isStatus();
        this.message = status.getMessage();
    }

}
