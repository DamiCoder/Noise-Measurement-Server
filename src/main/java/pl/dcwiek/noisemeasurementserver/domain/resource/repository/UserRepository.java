package pl.dcwiek.noisemeasurementserver.domain.resource.repository;

import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;

public interface UserRepository {

    AppUser getUser(String username, String password) throws NoSuchUserException;

    AppUser createUser(String username, String password) throws UsernameAlreadyExistsException;

    void updateAppUser(AppUser appUser) throws NoSuchUserException;
}
