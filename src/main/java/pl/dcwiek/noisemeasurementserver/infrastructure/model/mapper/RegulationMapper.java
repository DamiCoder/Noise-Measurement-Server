package pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper;

import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.Regulation;


public class RegulationMapper {

    public static RegulationModel mapEntityToModel(RegulationEntity entity) {
        return new RegulationModel(entity.getId(), entity.getRegulation().name());
    }

    public static RegulationEntity mapModelToEntity(RegulationModel model) {
        Regulation regulation = Regulation.valueOf(model.getName());
        return new RegulationEntity(model.getId(), regulation);
    }

}
