package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findAll();
}
