package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ProbeRepository {

    List<ProbeModel> findByUserIdAndOrderByCreatedDate(int userId, Integer number, Integer pageSize) throws NoSuchUserException;

    ProbeModel createProbeModel(String location, Integer placeId, int userId, int standardId, Integer result, String comment, LocalDateTime createdDate) throws NoSuchUserException, DataMissingException;

}
