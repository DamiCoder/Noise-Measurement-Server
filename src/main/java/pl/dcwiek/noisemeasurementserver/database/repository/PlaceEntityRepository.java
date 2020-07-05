package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;

import java.util.Optional;

public interface PlaceEntityRepository extends JpaRepository<PlaceEntity, Integer> {

    Optional<PlaceEntity> findByName(String name);
}
