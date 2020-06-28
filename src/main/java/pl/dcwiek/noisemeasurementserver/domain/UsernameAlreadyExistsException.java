package pl.dcwiek.noisemeasurementserver.domain;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException() {
        super("Username is already taken");
    }
}
