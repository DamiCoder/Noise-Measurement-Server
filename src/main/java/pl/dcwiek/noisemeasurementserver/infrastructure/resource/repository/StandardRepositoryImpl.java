package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.StandardRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.RegulationEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper.StandardMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
class StandardRepositoryImpl implements StandardRepository {

    private final StandardEntityRepository standardEntityRepository;
    private final RegulationEntityRepository regulationEntityRepository;
    private final PlaceEntityRepository placeEntityRepository;

    @Autowired
    public StandardRepositoryImpl(StandardEntityRepository standardEntityRepository, RegulationEntityRepository regulationEntityRepository, PlaceEntityRepository placeEntityRepository) {
        this.standardEntityRepository = standardEntityRepository;
        this.regulationEntityRepository = regulationEntityRepository;
        this.placeEntityRepository = placeEntityRepository;
    }

    @Override
    @Transactional
    public StandardModel createStandardModel(String title, String description, int minValue, int maxValue, int regulationId, int placeId) throws DataAlreadyExistsException, DataMissingException {
        RegulationEntity regulation = regulationEntityRepository.findById(regulationId).orElse(null);
        if(regulation == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", regulationId));
        }
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);
        if(place == null) {
            throw new DataMissingException(String.format("There is no place with id '%s'", placeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndRegulationAndPlace(title, regulation, place).orElse(null);
        if(standard != null) {
            throw new DataAlreadyExistsException(String.format("Standard with title '%s' and type '%s' already exists in the system", title, regulation.getRegulation().name()));
        }

        standard = standardEntityRepository.save(new StandardEntity(0, title, description, minValue, maxValue, regulation, place));
        return StandardMapper.mapEntityToModel(standard);
    }

    @Override
    public StandardModel getStandardModel(Integer id) {
        StandardEntity standard = standardEntityRepository.findById(id).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public StandardModel getStandardModel(String title, int regulationid, int placeId) throws DataMissingException {
        RegulationEntity regulation = regulationEntityRepository.findById(regulationid).orElse(null);
        if(regulation == null) {
            throw new DataMissingException(String.format("There is no type with id '%s'", regulationid));
        }
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);
        if(place == null) {
            throw new DataMissingException(String.format("There is no place with id '%s'", placeId));
        }
        StandardEntity standard = standardEntityRepository.findByTitleAndRegulationAndPlace(title, regulation, place).orElse(null);
        return standard != null ? StandardMapper.mapEntityToModel(standard) : null;
    }

    @Override
    public List<StandardModel> getStandards() {
        return standardEntityRepository.findAll().stream().map(StandardMapper::mapEntityToModel).collect(Collectors.toList());
    }
}
