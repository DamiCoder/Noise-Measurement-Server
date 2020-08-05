package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.RegulationRepository;

import java.util.List;

@Service
public class RegulationService {

    private final RegulationRepository regulationRepository;

    @Autowired
    public RegulationService(RegulationRepository regulationRepository) {
        this.regulationRepository = regulationRepository;
    }

    public RegulationModel getOrCreateRegulation(String name) {
        try {
            return regulationRepository.createRegulationModel(name);
        } catch (DataAlreadyExistsException e) {
            return regulationRepository.getRegulationModel(name);
        }
    }

    public List<RegulationModel> getRegulations() {
        return regulationRepository.getRegulations();
    }
}
