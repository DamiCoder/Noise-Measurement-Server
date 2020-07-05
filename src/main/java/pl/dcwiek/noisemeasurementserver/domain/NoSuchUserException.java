package pl.dcwiek.noisemeasurementserver.domain;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
