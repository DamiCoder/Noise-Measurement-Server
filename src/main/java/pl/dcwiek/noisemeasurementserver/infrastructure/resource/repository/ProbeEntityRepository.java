package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserEntity;

public interface ProbeEntityRepository extends JpaRepository<ProbeEntity, Integer> {

    Page<ProbeEntity> findByUserOrderByCreatedDateDesc(UserEntity user, Pageable pageable);
}
