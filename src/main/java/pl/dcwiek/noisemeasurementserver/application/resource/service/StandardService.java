package pl.dcwiek.noisemeasurementserver.application.resource.service;

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

    public StandardModel getOrCreateStandard(String title, String description, int maxValue, int minValue, int typeId, int placeId) throws ServiceException {
        try{
            try{
                return standardRepository.createStandardModel(title, description, minValue, maxValue, typeId, placeId);
            } catch (DataAlreadyExistsException e) {
                return standardRepository.getStandardModel(title, typeId, placeId);
            }
        } catch (DataMissingException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<StandardModel> getMatchingStandard(int result, int typeId, int placeId) throws ServiceException {
        try {
            return standardRepository.getMatchingStandardModels(typeId, result, placeId);
        } catch (DataMissingException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
