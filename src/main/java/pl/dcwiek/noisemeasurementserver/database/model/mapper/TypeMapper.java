package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Regulation;
import pl.dcwiek.noisemeasurementserver.domain.resource.TypeModel;


public class TypeMapper {

    public static TypeModel mapEntityToModel(TypeEntity entity) {
        return new TypeModel(entity.getId(), entity.getRegulation().name());
    }

    public static TypeEntity mapModelToEntity(TypeModel model) {
        Regulation regulation = Regulation.valueOf(model.getName());
        return new TypeEntity(model.getId(), regulation);
    }

}
