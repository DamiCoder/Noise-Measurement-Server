package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;

import java.util.List;

public interface ProbeEntityRepository extends JpaRepository<ProbeEntity, Integer> {

    List<ProbeEntity> findByUserOrderByCreatedDate(UserEntity userEntity);
}
