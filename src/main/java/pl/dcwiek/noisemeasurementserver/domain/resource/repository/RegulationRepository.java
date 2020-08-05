package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.DataAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;

import java.util.List;

public interface RegulationRepository {

    RegulationModel createRegulationModel(String name) throws DataAlreadyExistsException;

    RegulationModel getRegulationModel(Integer id);

    RegulationModel getRegulationModel(String name);

    List<RegulationModel> getRegulations();
}
