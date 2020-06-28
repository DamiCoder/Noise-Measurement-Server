package pl.dcwiek.noisemeasurementserver.application.resource.user.creation;


import lombok.Data;
import lombok.ToString;

@Data
public class CreateUserCommand {

    private final String username;
    @ToString.Exclude
    private final String password;
}
