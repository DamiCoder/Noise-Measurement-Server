package pl.dcwiek.noisemeasurementserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.StandardRepository;

import java.util.List;

@Service
public class StandardService {

    private final StandardRepository standardRepository;

    @Autowired
    StandardService(StandardRepository standardRepository) {
        this.standardRepository = standardRepository;
    }

    public List<StandardModel> getStandards() {
        return standardRepository.getStandards();
    }
}
