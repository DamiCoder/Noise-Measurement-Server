package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.StandardRepository;

@Service
public class StandardService {

    private final StandardRepository standardRepository;

    @Autowired
    StandardService(StandardRepository standardRepository) {
        this.standardRepository = standardRepository;
    }

    public StandardModel getOrCreateStandard(String title, String description, int maxValue, int minValue, int typeId) throws ServiceException {
        try{
            try{
                return standardRepository.createStandardModel(title, description, minValue, maxValue, typeId);
            } catch (DataAlreadyExistsException e) {
                return standardRepository.getStandardModel(title, typeId);
            }
        } catch (DataMissingException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public StandardModel getMatchingStandard(int result, int typeId) throws ServiceException {
        try {
            return standardRepository.getMatchingStandardModel(typeId, result);
        } catch (DataMissingException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
