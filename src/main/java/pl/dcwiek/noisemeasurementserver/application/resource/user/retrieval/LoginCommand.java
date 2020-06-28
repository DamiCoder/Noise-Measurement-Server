package pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval;


import lombok.Data;
import lombok.ToString;

@Data
public class LoginCommand {

    private final String username;
    @ToString.Exclude
    private final String password;
}
