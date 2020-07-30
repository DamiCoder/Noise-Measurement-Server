package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;

public class StandardMapper {

    public static StandardModel mapEntityToModel(StandardEntity entity) {
        return new StandardModel(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMinValue(),
                entity.getMaxValue(),
                TypeMapper.mapEntityToModel(entity.getType()),
                PlaceMapper.mapEntityToModel(entity.getPlace()));
    }
}
