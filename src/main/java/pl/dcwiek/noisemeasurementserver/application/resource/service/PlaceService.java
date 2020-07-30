package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Place;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.PlaceRepository;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PlaceModel getOrCreatePlace(String name, String description, String type) {
        try {
            return placeRepository.createPlaceModel(name, description, type);
        } catch (DataAlreadyExistsException e) {
            return placeRepository.getPlaceModel(name);
        }
    }

    public int getDefaultPlaceId() throws DataMissingException {
        PlaceModel defaultPlace = placeRepository.getPlaceModel(Place.CITY_OUTSIDE.name());
        if (defaultPlace != null) {
            return defaultPlace.getId();
        } else {
            throw new DataMissingException("There is no default place in DB");
        }

    }
}
