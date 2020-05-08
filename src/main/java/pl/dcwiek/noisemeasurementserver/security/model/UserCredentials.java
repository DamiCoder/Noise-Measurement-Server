package pl.dcwiek.noisemeasurementserver.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserCredentials implements Serializable {
    private final String username;
    private final String password;
}
