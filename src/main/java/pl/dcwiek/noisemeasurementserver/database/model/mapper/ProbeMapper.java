package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;

public class ProbeMapper {

    public static ProbeModel mapEntityToModel(ProbeEntity entity) {
        return new ProbeModel(entity.getId(),
                entity.getLocation(),
                PlaceMapper.mapEntityToModel(entity.getPlace()),
                StandardMapper.mapEntityToModel(entity.getStandard()),
                UserMapper.mapEntityToModel(entity.getUser()),
                entity.getResult(),
                entity.getComment(),
                entity.getCreatedDate());
    }
}
