package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.PlaceEntity;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer> {


}
