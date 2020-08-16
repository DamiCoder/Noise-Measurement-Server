package pl.dcwiek.noisemeasurementserver.application.resource.probe.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.application.resource.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.application.sound.SoundLevelService;

@Service
@Slf4j
public class ProbeProcessingService {

    private final SoundLevelService soundLevelService;

    @Autowired
    public ProbeProcessingService(SoundLevelService soundLevelService) {
        this.soundLevelService = soundLevelService;
    }

    public double countDbLevel(CountDbLevelCommand command) throws ServiceException {
        log.debug("ProbeProcessingService.countDbLevel method invoked: " + command.toString());

        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        soundLevelService.initDispatcher(command.getProbe().getAbsolutePath());
        try {
            soundLevelService.startSoundAnalyzer(command.getAmplitudeReferenceValue());
        } catch (InterruptedException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return soundLevelService.countDbLevel();
    }
}
