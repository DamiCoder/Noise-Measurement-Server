package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.Regulation;

import java.util.Optional;

public interface RegulationEntityRepository extends JpaRepository<RegulationEntity, Integer> {

    Optional<RegulationEntity> findByRegulation(Regulation regulation);
}
