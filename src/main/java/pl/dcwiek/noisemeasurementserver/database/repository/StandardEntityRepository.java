package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;

import java.util.List;
import java.util.Optional;

public interface StandardEntityRepository extends JpaRepository<StandardEntity, Integer> {

    List<StandardEntity> getAll();

    Optional<StandardEntity> findByTitleAndType(String title, TypeEntity type);

    Optional<StandardEntity> findByMinValueLessThanEqualAndMaxValueGreaterThanAndTypeEquals(Integer minValue, Integer maxValue, TypeEntity type);
}
