package pl.dcwiek.noisemeasurementserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
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

    public StandardModel getOrCreateStandard(String title, String description, int maxValue, int minValue, int regulationId, int placeId) throws ServiceException {
        try {
            try {
                return standardRepository.createStandardModel(title, description, minValue, maxValue, regulationId, placeId);
            } catch (DataAlreadyExistsException e) {
                return standardRepository.getStandardModel(title, regulationId, placeId);
            }
        } catch (DataMissingException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public StandardModel getStandard(int standardId) {
        return standardRepository.getStandardModel(standardId);

    }

    public List<StandardModel> getStandards() {
        return standardRepository.getStandards();
    }
}
