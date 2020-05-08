package pl.dcwiek.noisemeasurementserver.web.user.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.database.model.User;
import pl.dcwiek.noisemeasurementserver.database.model.UserRole;
import pl.dcwiek.noisemeasurementserver.database.model.constants.ApplicationUserRole;
import pl.dcwiek.noisemeasurementserver.database.repo.UserRoleRepository;
import pl.dcwiek.noisemeasurementserver.security.model.UserCredentials;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserMapper(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public User mapCredentialsToRegularUser(UserCredentials userCredentials) {
        LocalDateTime createDate = LocalDateTime.now();
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.findByApplicationUserRole(ApplicationUserRole.USER));
        return new User(0, userCredentials.getUsername(), userCredentials.getPassword(), createDate, userRoles);
    }
}
