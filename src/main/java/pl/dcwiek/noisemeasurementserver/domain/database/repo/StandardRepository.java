package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.StandardEntity;

import java.util.List;

public interface StandardRepository extends JpaRepository<StandardEntity, Integer> {

    List<StandardEntity> getAll();
}
