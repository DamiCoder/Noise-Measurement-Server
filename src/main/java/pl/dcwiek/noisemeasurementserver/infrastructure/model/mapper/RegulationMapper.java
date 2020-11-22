package pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper;

import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;


public class RegulationMapper {

    public static RegulationModel mapEntityToModel(RegulationEntity entity) {
        return new RegulationModel(entity.getId(), entity.getRegulation().name());
    }
}
