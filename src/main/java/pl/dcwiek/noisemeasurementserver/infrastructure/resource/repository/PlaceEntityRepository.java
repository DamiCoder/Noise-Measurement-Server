package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.PlaceEntity;

import java.util.Optional;

public interface PlaceEntityRepository extends JpaRepository<PlaceEntity, Integer> {

    Optional<PlaceEntity> findByName(String name);
}
