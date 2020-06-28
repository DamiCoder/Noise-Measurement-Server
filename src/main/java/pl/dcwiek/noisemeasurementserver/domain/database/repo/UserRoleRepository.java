package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserRoleEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {

    UserRoleEntity findByUserRole(UserRole userRole);
}
