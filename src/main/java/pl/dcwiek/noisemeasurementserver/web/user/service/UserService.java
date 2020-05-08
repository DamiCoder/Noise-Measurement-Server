package pl.dcwiek.noisemeasurementserver.web.user.service;

import pl.dcwiek.noisemeasurementserver.database.model.User;
import pl.dcwiek.noisemeasurementserver.security.model.UserCredentials;
import pl.dcwiek.noisemeasurementserver.web.user.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.web.user.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;

public interface UserService {

    User getUserWithoutConfidentialData(UserCredentials userCredentials)
            throws ServiceException, UserCredentialsException;

    User createUser(UserRegistrationForm userRegistrationForm)
            throws ServiceException, UsernameAlreadyExistsException;
}
