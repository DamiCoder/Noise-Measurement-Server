package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.Standard;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {

    TypeEntity getTypeByStandard(Standard standard);
}
