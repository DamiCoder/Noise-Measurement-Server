package pl.dcwiek.noisemeasurementserver.security.model;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AppUser {

    private final Integer id;

    private final String username;

    private final LocalDateTime createdDate;

    private final boolean firstLogIn;

    private final Set<UserRoleEntity> userRoles;

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", createdDate=" + createdDate +
                ", userRoles=" + userRoles +
                '}';
    }
}
