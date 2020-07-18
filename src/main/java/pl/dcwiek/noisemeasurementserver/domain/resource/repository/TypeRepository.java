package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.TypeModel;

import java.util.List;

public interface TypeRepository {

    TypeModel createTypeModel(String name) throws DataAlreadyExistsException;

    TypeModel getTypeModel(Integer id);

    TypeModel getTypeModel(String name);

    List<TypeModel> getTypes();
}
