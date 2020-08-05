package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Regulation;
import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;


public class RegulationMapper {

    public static RegulationModel mapEntityToModel(RegulationEntity entity) {
        return new RegulationModel(entity.getId(), entity.getRegulation().name());
    }

    public static RegulationEntity mapModelToEntity(RegulationModel model) {
        Regulation regulation = Regulation.valueOf(model.getName());
        return new RegulationEntity(model.getId(), regulation);
    }

}
