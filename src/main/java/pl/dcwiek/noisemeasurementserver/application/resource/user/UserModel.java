package pl.dcwiek.noisemeasurementserver.application.resource.user;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserModel {

    private final Integer id;

    private final String username;

    private final LocalDateTime createdDate;

    private final Set<UserRoleEntity> userRoles;
}
