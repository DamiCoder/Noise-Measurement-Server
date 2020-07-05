package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findAll();
}
