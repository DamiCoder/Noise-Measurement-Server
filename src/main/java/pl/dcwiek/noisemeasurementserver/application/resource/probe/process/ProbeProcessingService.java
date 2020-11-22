package pl.dcwiek.noisemeasurementserver.application.resource.probe.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.sound.SoundLevelService;
import pl.dcwiek.noisemeasurementserver.infrastructure.sound.SoundLevelServiceImpl;

@Service
@Slf4j
public class ProbeProcessingService {

    private final SoundLevelService soundLevelService;

    @Autowired
    public ProbeProcessingService(SoundLevelServiceImpl soundLevelService) {
        this.soundLevelService = soundLevelService;
    }

    public double countDbLevel(CountDbLevelCommand command) throws ServiceException {
        log.debug("ProbeProcessingService.countDbLevel method invoked: " + command.toString());

        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        try {
            return soundLevelService.countSoundDbLevel(command.getProbe().getAbsolutePath(), command.getAmplitudeReferenceValue());
        } catch (InterruptedException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
