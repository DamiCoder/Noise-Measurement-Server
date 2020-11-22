package pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper;

import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.ProbeEntity;

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
                entity.getUserRating(),
                entity.getCreatedDate());
    }
}
