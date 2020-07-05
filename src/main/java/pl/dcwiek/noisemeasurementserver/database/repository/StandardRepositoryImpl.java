package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.mapper.StandardMapper;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.StandardRepository;

import javax.transaction.Transactional;

@Service
class StandardRepositoryImpl implements StandardRepository {

    private final StandardEntityRepository standardEntityRepository;
    private final TypeEntityRepository typeEntityRepository;

    @Autowired
    public StandardRepositoryImpl(StandardEntityRepository standardEntityRepository, TypeEntityRepository typeEntityRepository) {
        this.standardEntityRepository = standardEntityRepository;
        this.typeEntityRepository = typeEntityRepository;
    }

    @Override
    @Transactional
    public StandardModel createStandardModel(String title, String description, int minValue, int maxValue, int typeId) throws DataAlreadyExistsException, DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndType(title, type).orElse(null);
        if(standard != null) {
            throw new DataAlreadyExistsException(String.format("Standard with title '%s' and type '%s' already exists in the system", title, type.getRegulation().name()));
        }

        standard = standardEntityRepository.save(new StandardEntity(0, title, description, minValue, maxValue, type));
        return StandardMapper.mapEntityToModel(standard);
    }

    @Override
    public StandardModel getStandardModel(Integer id) {
        StandardEntity standard = standardEntityRepository.findById(id).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public StandardModel getStandardModel(String title, int typeId) throws DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndType(title, type).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public StandardModel getMatchingStandardModel(int typeId, int result) throws DataMissingException {
        TypeEntity type = typeEntityRepository.findById(typeId).orElse(null);
        if(type == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", typeId));
        }
        StandardEntity standard = standardEntityRepository.findByMinValueLessThanEqualAndMaxValueGreaterThanAndTypeEquals(result, result, type).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }
}
