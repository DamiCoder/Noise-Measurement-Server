package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Regulation;

import java.util.Optional;

public interface TypeEntityRepository extends JpaRepository<TypeEntity, Integer> {

    Optional<TypeEntity> findByRegulation(Regulation regulation);
}
