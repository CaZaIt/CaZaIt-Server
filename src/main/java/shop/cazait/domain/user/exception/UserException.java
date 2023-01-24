package shop.cazait.domain.user.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.cazait.global.error.status.ErrorStatus;

@AllArgsConstructor
@Getter
public class UserException extends Exception{
    private ErrorStatus error;
}
