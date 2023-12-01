package Secret.Santa.Secret.Santa.exception;


public class SantaServiceDisabledException extends RuntimeException {


    public SantaServiceDisabledException() {
    }

    public SantaServiceDisabledException(String message) {
        super(message);
    }

}
