package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.UserRoleEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.UserRole;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Integer> {

    UserRoleEntity findByUserRole(UserRole userRole);
}
