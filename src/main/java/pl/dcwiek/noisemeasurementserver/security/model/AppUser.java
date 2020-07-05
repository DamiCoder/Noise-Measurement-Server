package pl.dcwiek.noisemeasurementserver.security.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.database.model.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AppUser {

    private final Integer id;

    private final String username;

    private final LocalDateTime createdDate;

    private final Set<UserRoleEntity> userRoles;
}
