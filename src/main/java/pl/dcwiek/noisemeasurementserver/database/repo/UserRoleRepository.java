package pl.dcwiek.noisemeasurementserver.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.UserRole;
import pl.dcwiek.noisemeasurementserver.database.model.constants.ApplicationUserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findByApplicationUserRole(ApplicationUserRole applicationUserRole);
}
