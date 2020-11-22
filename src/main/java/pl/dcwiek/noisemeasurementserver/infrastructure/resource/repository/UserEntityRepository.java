package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserEntity;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findAll();
}
