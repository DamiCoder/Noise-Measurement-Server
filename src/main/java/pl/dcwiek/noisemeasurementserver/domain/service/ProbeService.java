package pl.dcwiek.noisemeasurementserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.ProbeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProbeService {

    private final ProbeRepository probeRepository;

    @Autowired
    public ProbeService(ProbeRepository probeRepository) {
        this.probeRepository = probeRepository;
    }

    public ProbeModel createProbe(String location, Integer placeId, int userId, Integer result, List<Integer> standardIds, String comment, Integer userRating) throws ServiceException {
        try {
            return probeRepository.createProbeModel(
                    location,
                    placeId,
                    userId,
                    result,
                    standardIds,
                    comment,
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                    userRating);
        } catch (DataMissingException | NoSuchUserException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<ProbeModel> getProbes(int userId, Integer count, Integer pageSize) throws ServiceException {
        try{
            return probeRepository.findByUserIdAndOrderByCreatedDate(userId, count, pageSize);
        } catch (NoSuchUserException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
