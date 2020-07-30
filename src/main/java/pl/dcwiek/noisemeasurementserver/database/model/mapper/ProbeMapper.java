package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;

import java.util.stream.Collectors;

public class ProbeMapper {

    public static ProbeModel mapEntityToModel(ProbeEntity entity) {
        return new ProbeModel(entity.getId(),
                entity.getLocation(),
                PlaceMapper.mapEntityToModel(entity.getPlace()),
                UserMapper.mapEntityToModel(entity.getUser()),
                entity.getResult(),
                entity.getStandards().stream().map(StandardMapper::mapEntityToModel).collect(Collectors.toList()),
                entity.getComment(),
                entity.getCreatedDate());
    }
}
