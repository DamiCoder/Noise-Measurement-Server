package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

public interface UserRepository {

    AppUser getUser(String username, String password) throws NoSuchUserException;

    AppUser createUser(String username, String password) throws UsernameAlreadyExistsException, ServiceException;

    void updateAppUser(AppUser appUser) throws NoSuchUserException;
}
