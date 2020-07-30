package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;

import java.util.List;

public interface StandardRepository {

    StandardModel createStandardModel(String title, String description, int minValue, int maxValue, int typeId, int placeId) throws DataAlreadyExistsException, DataMissingException;

    StandardModel getStandardModel(Integer id);

    StandardModel getStandardModel(String title, int typeId, int placeId) throws DataMissingException;

    List<StandardModel> getMatchingStandardModels(int typeId, int result, int placeId) throws DataMissingException;
}
