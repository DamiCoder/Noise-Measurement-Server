package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dcwiek.noisemeasurementserver.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;

public interface ProbeEntityRepository extends JpaRepository<ProbeEntity, Integer> {

    Page<ProbeEntity> findByUserOrderByCreatedDateDesc(UserEntity user, Pageable pageable);

    int countByUser(UserEntity user);
}
