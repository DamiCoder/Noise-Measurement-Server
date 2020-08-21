package pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper;

import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.PlaceEntity;

public class PlaceMapper {

    public static PlaceModel mapEntityToModel(PlaceEntity entity) {
        return new PlaceModel(entity.getId(), entity.getName(), entity.getDescription(), entity.getType(),RegulationMapper.mapEntityToModel(entity.getRegulation()));
    }
}
