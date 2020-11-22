package pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper;

import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.StandardEntity;

public class StandardMapper {

    public static StandardModel mapEntityToModel(StandardEntity entity) {
        return new StandardModel(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMinValue(),
                entity.getMaxValue(),
                RegulationMapper.mapEntityToModel(entity.getRegulation()),
                PlaceMapper.mapEntityToModel(entity.getPlace()));
    }
}
