package pl.dcwiek.noisemeasurementserver.application.resource.probe.creation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.service.ProbeService;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;

@Service
@Slf4j
public class CreateProbeService {


    private final ProbeService probeService;

    @Autowired
    public CreateProbeService(ProbeService probeService) {
        this.probeService = probeService;
    }

    public ProbeModel createProbe(CreateProbeCommand command) throws ServiceException {

        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        log.info("CreateProbeService.create probe method invoked: " + command);

        return probeService.createProbe(command.getLocation(),
                command.getPlaceId(),
                command.getUserId(),
                command.getResult(),
                command.getStandardIds(),
                command.getComment(),
                command.getUserRating());
    }
}
