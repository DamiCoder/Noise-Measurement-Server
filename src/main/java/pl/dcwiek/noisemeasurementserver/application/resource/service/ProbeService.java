package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.ProbeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProbeService {

    private final ProbeRepository probeRepository;
    private final PlaceService placeService;

    @Autowired
    public ProbeService(ProbeRepository probeRepository, PlaceService placeService) {
        this.probeRepository = probeRepository;
        this.placeService = placeService;
    }

    public ProbeModel createProbe(String location, Integer placeId, int userId, int standardId, Integer result, String comment) throws ServiceException {
        try {
            if (placeId == null) {
                return probeRepository.createProbeModel(
                        location,
                        placeService.getDefaultPlaceId(),
                        userId,
                        standardId,
                        result,
                        comment,
                        LocalDateTime.now());
            }
            return probeRepository.createProbeModel(
                    location,
                    placeId,
                    userId,
                    standardId,
                    result,
                    comment,
                    LocalDateTime.now());
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
