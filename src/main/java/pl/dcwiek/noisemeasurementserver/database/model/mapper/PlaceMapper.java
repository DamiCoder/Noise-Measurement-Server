package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;

public class PlaceMapper {

    public static PlaceModel mapEntityToModel(PlaceEntity entity) {
        return new PlaceModel(entity.getId(), entity.getName(), entity.getDescription());
    }
}
