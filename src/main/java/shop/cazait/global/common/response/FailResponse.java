package shop.cazait.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;



@Getter
@JsonPropertyOrder({"result", "message", "description"})
public class FailResponse {

    private String result;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public FailResponse(ErrorStatus status) {
        this.result = status.getResult();
        this.message = status.getMessage();
    }

    public FailResponse(ErrorStatus status, String description) {
        this.result = status.getResult();
        this.message = status.getMessage();
        this.description = description;
    }

}
