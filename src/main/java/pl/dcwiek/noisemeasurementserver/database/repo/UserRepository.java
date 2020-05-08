package pl.dcwiek.noisemeasurementserver.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findAll();
}
