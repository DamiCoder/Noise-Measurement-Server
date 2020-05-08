package pl.dcwiek.noisemeasurementserver.web.user;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException() {
        super("Username is already taken");
    }
}
