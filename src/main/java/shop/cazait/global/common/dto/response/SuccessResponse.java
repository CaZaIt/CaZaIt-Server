package shop.cazait.global.common.dto.response;

import static shop.cazait.global.common.constant.Constant.SUCCESS;
import static shop.cazait.global.common.constant.Constant.SUCCESS_MESSAGE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@JsonPropertyOrder({"code", "result", "message", "data"})
public class SuccessResponse<T> {

    private int code;
    private String result;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public SuccessResponse(T data) {
        this.code = HttpStatus.OK.value();
        this.result = SUCCESS;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

}
