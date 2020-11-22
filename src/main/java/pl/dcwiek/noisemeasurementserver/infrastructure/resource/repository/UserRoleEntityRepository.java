package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserRoleEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Integer> {

    UserRoleEntity findByUserRole(UserRole userRole);
}
