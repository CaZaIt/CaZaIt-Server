package shop.cazait.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;



@Getter
@JsonPropertyOrder({"result", "message"})
public class FailResponse {

    private String result;
    private String message;

    public FailResponse(ErrorStatus status) {
        this.result = status.getResult();
        this.message = status.getMessage();
    }

}
