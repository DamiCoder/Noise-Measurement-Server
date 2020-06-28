package pl.dcwiek.noisemeasurementserver.application.resource.user.creation;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserCommand {

    private final String username;
    private final String password;

    @Override
    public String toString() {
        return "CreateUserCommand{" +
                "username='" + username + '\'' +
                '}';
    }
}
