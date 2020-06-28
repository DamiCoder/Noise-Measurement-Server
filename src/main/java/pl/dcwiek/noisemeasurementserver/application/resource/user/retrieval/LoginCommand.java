package pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCommand {

    private final String username;
    private final String password;

    @Override
    public String toString() {
        return "LoginCommand{" +
                "username='" + username + '\'' +
                '}';
    }
}
