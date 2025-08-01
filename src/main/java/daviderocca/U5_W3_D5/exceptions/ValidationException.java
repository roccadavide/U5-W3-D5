package daviderocca.U5_W3_D5.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private String errorMessages;

    public ValidationException(String errorMessages) {
        super("Errori vari di validazione!");
        this.errorMessages = errorMessages;
    }

    private List<String> errorsMessages;

    public ValidationException(List<String> errorMessages) {
        super("Errori vari di validazione!");
        this.errorsMessages = errorMessages;
    }
}