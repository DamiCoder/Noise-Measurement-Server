package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.TypeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.constants.Regulation;
import pl.dcwiek.noisemeasurementserver.database.model.mapper.TypeMapper;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.TypeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.TypeRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
class TypeRepositoryImpl implements TypeRepository {

    private final TypeEntityRepository typeEntityRepository;

    @Autowired
    public TypeRepositoryImpl(TypeEntityRepository typeEntityRepository) {
        this.typeEntityRepository = typeEntityRepository;
    }

    @Override
    @Transactional
    public TypeModel createTypeModel(String name) throws DataAlreadyExistsException {
        Regulation regulation = Regulation.valueOf(name);
        TypeEntity type = typeEntityRepository.findByRegulation(regulation).orElse(null);
        if (type != null) {
            throw new DataAlreadyExistsException(String.format("Type with name '%s' already exists in the system", name));
        }
        type = typeEntityRepository.save(new TypeEntity(0, regulation));
        return TypeMapper.mapEntityToModel(type);
    }

    @Override
    public TypeModel getTypeModel(Integer id) {
        TypeEntity type = typeEntityRepository.findById(id).orElse(null);
        return type != null ? TypeMapper.mapEntityToModel(type) : null;
    }

    @Override
    public TypeModel getTypeModel(String name) {
        Regulation regulation = Regulation.valueOf(name);
        TypeEntity type = typeEntityRepository.findByRegulation(regulation).orElse(null);
        return type != null ? TypeMapper.mapEntityToModel(type) : null;
    }

    @Override
    public List<TypeModel> getTypes() {
        List<TypeEntity> typeEntities = typeEntityRepository.findAll();
        return typeEntities.size() != 0
                ? typeEntities.stream().map(TypeMapper::mapEntityToModel).collect(Collectors.toList())
                : Collections.emptyList();
    }
}

