package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Regulation;

import java.util.Optional;

public interface RegulationEntityRepository extends JpaRepository<RegulationEntity, Integer> {

    Optional<RegulationEntity> findByRegulation(Regulation regulation);
}
