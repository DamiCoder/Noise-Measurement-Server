package pl.dcwiek.noisemeasurementserver.web.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserRegistrationForm implements Serializable {
    private final String username;
    private final String password;
}

