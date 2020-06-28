package pl.dcwiek.noisemeasurementserver.domain.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;

import java.util.List;

public interface ProbeRepository extends JpaRepository<ProbeEntity, Integer> {

    List<ProbeEntity> getProbesByUserAndOrderByCreatedDate(UserEntity user);

}
