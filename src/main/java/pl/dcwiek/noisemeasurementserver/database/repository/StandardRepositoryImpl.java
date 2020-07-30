package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.mapper.StandardMapper;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.StandardRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
class StandardRepositoryImpl implements StandardRepository {

    private final StandardEntityRepository standardEntityRepository;
    private final TypeEntityRepository typeEntityRepository;
    private final PlaceEntityRepository placeEntityRepository;

    @Autowired
    public StandardRepositoryImpl(StandardEntityRepository standardEntityRepository, TypeEntityRepository typeEntityRepository, PlaceEntityRepository placeEntityRepository) {
        this.standardEntityRepository = standardEntityRepository;
        this.typeEntityRepository = typeEntityRepository;
        this.placeEntityRepository = placeEntityRepository;
    }

    @Override
    @Transactional
    public StandardModel createStandardModel(String title, String description, int minValue, int maxValue, int typeId, int placeId) throws DataAlreadyExistsException, DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);
        if(place == null) {
            throw new DataMissingException(String.format("There is no place with id '%s'", placeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndTypeAndPlace(title, type, place).orElse(null);
        if(standard != null) {
            throw new DataAlreadyExistsException(String.format("Standard with title '%s' and type '%s' already exists in the system", title, type.getRegulation().name()));
        }

        standard = standardEntityRepository.save(new StandardEntity(0, title, description, minValue, maxValue, type, place));
        return StandardMapper.mapEntityToModel(standard);
    }

    @Override
    public StandardModel getStandardModel(Integer id) {
        StandardEntity standard = standardEntityRepository.findById(id).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public StandardModel getStandardModel(String title, int typeId, int placeId) throws DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);
        if(place == null) {
            throw new DataMissingException(String.format("There is no place with id '%s'", placeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndTypeAndPlace(title, type, place).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public List<StandardModel> getMatchingStandardModels(int typeId, int result, int placeId) throws DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);
        if(place == null) {
            throw new DataMissingException(String.format("There is no place with id '%s'", placeId));
        }
        List<StandardEntity> standards = standardEntityRepository.findAllByMinValueLessThanEqualAndMaxValueGreaterThanEqualAndTypeAndPlace(result, result, type, place);
        return standards.stream().map(StandardMapper::mapEntityToModel).collect(Collectors.toList());
    }
}
