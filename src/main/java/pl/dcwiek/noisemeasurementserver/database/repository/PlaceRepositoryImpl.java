package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.database.model.mapper.PlaceMapper;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.PlaceRepository;

import javax.transaction.Transactional;

@Service
class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceEntityRepository placeEntityRepository;

    @Autowired
    public PlaceRepositoryImpl(PlaceEntityRepository placeEntityRepository) {
        this.placeEntityRepository = placeEntityRepository;
    }

    @Override
    @Transactional
    public PlaceModel createPlaceModel(String name, String description, String type) throws DataAlreadyExistsException {
        PlaceEntity place = placeEntityRepository.findByName(name).orElse(null);
        if(place != null) {
            throw new DataAlreadyExistsException(String.format("Place with name '%s' already exists in the system", name));
        }

        place = placeEntityRepository.save(new PlaceEntity(0, name, description, type));
        return PlaceMapper.mapEntityToModel(place);
    }

    @Override
    public PlaceModel getPlaceModel(Integer id) {
        PlaceEntity place = placeEntityRepository.findById(id).orElse(null);
        return place != null ? PlaceMapper.mapEntityToModel(place) : null;
    }

    @Override
    public PlaceModel getPlaceModel(String name) {
        PlaceEntity place = placeEntityRepository.findByName(name).orElse(null);
        return place != null ? PlaceMapper.mapEntityToModel(place) : null;
    }
}
