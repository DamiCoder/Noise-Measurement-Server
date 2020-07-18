package pl.dcwiek.noisemeasurementserver.application.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.TypeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.TypeRepository;

import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public TypeModel getOrCreateType(String name) {
        try {
            return typeRepository.createTypeModel(name);
        } catch (DataAlreadyExistsException e) {
            return typeRepository.getTypeModel(name);
        }
    }

    public List<TypeModel> getTypeModels() {
        return typeRepository.getTypes();
    }
}
