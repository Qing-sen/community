package q.community.community.exception;

import org.springframework.core.annotation.Order;

public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message=errorCode.getMessage();
    }
    public CustomizeException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
