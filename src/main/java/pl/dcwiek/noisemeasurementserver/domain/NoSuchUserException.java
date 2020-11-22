package pl.dcwiek.noisemeasurementserver.domain;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String message) {
        super(message);
    }
}
