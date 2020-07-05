package pl.dcwiek.noisemeasurementserver.domain;

public class DataMissingException extends Exception {

    public DataMissingException(String message) {
        super(message);
    }

    public DataMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
