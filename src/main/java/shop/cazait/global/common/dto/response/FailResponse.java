package shop.cazait.global.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;



@Getter
@JsonPropertyOrder({"code", "result", "error", "message", "description"})
public class FailResponse {

    private int code;
    private String result;
    private ErrorStatus error;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public FailResponse(ErrorStatus status) {
        this.code = status.getCode();
        this.result = status.getResult();
        this.error = status;
        this.message = status.getMessage();
    }

    public FailResponse(ErrorStatus status, String description) {
        this.code = status.getCode();
        this.result = status.getResult();
        this.error = status;
        this.message = status.getMessage();
        this.description = description;
    }

}
