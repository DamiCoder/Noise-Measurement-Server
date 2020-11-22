package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.RegulationRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.Regulation;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper.RegulationMapper;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
class RegulationRepositoryImpl implements RegulationRepository {

    private final RegulationEntityRepository regulationEntityRepository;

    @Autowired
    RegulationRepositoryImpl(RegulationEntityRepository regulationEntityRepository) {
        this.regulationEntityRepository = regulationEntityRepository;
    }

    @Override
    @Transactional
    public RegulationModel createRegulationModel(String name) throws DataAlreadyExistsException {
        Regulation regulation = Regulation.valueOf(name);
        RegulationEntity type = regulationEntityRepository.findByRegulation(regulation).orElse(null);
        if (type != null) {
            throw new DataAlreadyExistsException(String.format("Type with name '%s' already exists in the system", name));
        }
        type = regulationEntityRepository.save(new RegulationEntity(0, regulation));
        return RegulationMapper.mapEntityToModel(type);
    }

    @Override
    public RegulationModel getRegulationModel(Integer id) {
        RegulationEntity type = regulationEntityRepository.findById(id).orElse(null);
        return type != null ? RegulationMapper.mapEntityToModel(type) : null;
    }

    @Override
    public RegulationModel getRegulationModel(String name) {
        Regulation regulation = Regulation.valueOf(name);
        RegulationEntity type = regulationEntityRepository.findByRegulation(regulation).orElse(null);
        return type != null ? RegulationMapper.mapEntityToModel(type) : null;
    }

    @Override
    public List<RegulationModel> getRegulations() {
        List<RegulationEntity> typeEntities = regulationEntityRepository.findAll();
        return typeEntities.size() != 0
                ? typeEntities.stream().map(RegulationMapper::mapEntityToModel).collect(Collectors.toList())
                : Collections.emptyList();
    }
}

