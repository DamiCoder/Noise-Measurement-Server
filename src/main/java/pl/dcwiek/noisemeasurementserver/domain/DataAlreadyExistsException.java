package pl.dcwiek.noisemeasurementserver.domain;


public class DataAlreadyExistsException extends Exception {

    public DataAlreadyExistsException(String message) {
        super(message);
    }

    public DataAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
