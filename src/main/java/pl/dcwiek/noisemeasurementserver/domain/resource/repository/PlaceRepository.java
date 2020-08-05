package pl.dcwiek.noisemeasurementserver.domain.resource.repository;


import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;

import java.util.List;

public interface PlaceRepository {

    PlaceModel createPlaceModel(String name, String description, String type, int regulationid) throws DataAlreadyExistsException, DataMissingException;

    PlaceModel getPlaceModel(Integer id);

    PlaceModel getPlaceModel(String name);

    List<PlaceModel> getAllPlaceModels();
}
