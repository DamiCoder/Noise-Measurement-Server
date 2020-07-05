package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;

public interface StandardRepository {

    StandardModel createStandardModel(String title, String description, int minValue, int maxValue, int typeId) throws DataAlreadyExistsException, DataMissingException;

    StandardModel getStandardModel(Integer id);

    StandardModel getStandardModel(String title, int typeId) throws DataMissingException;

    StandardModel getMatchingStandardModel(int typeId, int result) throws DataMissingException;
}
