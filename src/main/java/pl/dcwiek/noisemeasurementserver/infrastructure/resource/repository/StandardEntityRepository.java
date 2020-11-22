package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.StandardEntity;

import java.util.Optional;

public interface StandardEntityRepository extends JpaRepository<StandardEntity, Integer> {

    Optional<StandardEntity> findByTitleAndRegulationAndPlace(String title, RegulationEntity regulation, PlaceEntity place);
}
