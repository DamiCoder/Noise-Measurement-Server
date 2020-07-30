package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;

import java.util.List;
import java.util.Optional;

public interface StandardEntityRepository extends JpaRepository<StandardEntity, Integer> {

    Optional<StandardEntity> findByTitleAndTypeAndPlace(String title, TypeEntity type, PlaceEntity place);

    List<StandardEntity> findAllByMinValueLessThanEqualAndMaxValueGreaterThanEqualAndTypeAndPlace(Integer minValue, Integer maxValue, TypeEntity type, PlaceEntity place);
}
