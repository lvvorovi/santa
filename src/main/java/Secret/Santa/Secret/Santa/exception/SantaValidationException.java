package Secret.Santa.Secret.Santa.exception;


public class SantaValidationException extends RuntimeException {

    private String field;
    private String error;

    private String rejectedValue;

    public SantaValidationException() {
    }

    public SantaValidationException(String message, String field, String error, String rejectedValue) {
        super(message);
        this.field = field;
        this.error = error;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
}
