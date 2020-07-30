package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.ProbeRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class ProbeService {

    private final ProbeRepository probeRepository;

    @Autowired
    public ProbeService(ProbeRepository probeRepository) {
        this.probeRepository = probeRepository;
    }

    public ProbeModel createProbe(String location, Integer placeId, int userId, Integer result, String comment, Integer userRating) throws ServiceException {
        try {
            //TODO: try to calculate standards here!
            //TODO: 1. we should try to get MEDIUM risk standards (result >= min_value && result < max_value)
            //TODO: 2. we should try to get HIGH risk standards (result >= max_value)
            return probeRepository.createProbeModel(
                    location,
                    placeId,
                    userId,
                    result,
                    Collections.emptyList(),
                    comment,
                    LocalDateTime.now(),
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
