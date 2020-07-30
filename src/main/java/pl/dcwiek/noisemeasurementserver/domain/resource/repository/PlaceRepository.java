package pl.dcwiek.noisemeasurementserver.domain.resource.repository;


import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;

public interface PlaceRepository {

    PlaceModel createPlaceModel(String name, String description, String type) throws DataAlreadyExistsException;

    PlaceModel getPlaceModel(Integer id);

    PlaceModel getPlaceModel(String name);
}
