package pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.ProbeRepository;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;

import java.util.List;

@Service
@Slf4j
public class GetProbeService {

    private final ProbeRepository probeRepository;

    @Autowired
    public GetProbeService(ProbeRepository probeRepository) {
        this.probeRepository = probeRepository;
    }

    public List<ProbeModel> getUserProbeModels(GetProbesCommand command) throws ServiceException {
        log.debug("GetUserService.login method invoked: " + command.toString());

        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        try{
            return probeRepository.findByUserIdAndOrderByCreatedDate(command.getUserId(), command.getNumber(), command.getPageSize());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
