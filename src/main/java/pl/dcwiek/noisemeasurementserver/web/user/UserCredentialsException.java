package pl.dcwiek.noisemeasurementserver.web.user;

public class UserCredentialsException extends Exception {

    public UserCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCredentialsException(String message) {
        super(message);
    }
}
