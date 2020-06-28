package pl.dcwiek.noisemeasurementserver.domain.database.service;

import pl.dcwiek.noisemeasurementserver.application.resource.user.UserModel;
import pl.dcwiek.noisemeasurementserver.domain.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;

public interface UserService {

    UserEntity getUserWithoutConfidentialData(String username, String password) throws ServiceException, UserCredentialsException;

    UserModel createUser(String username, String password) throws ServiceException, UsernameAlreadyExistsException;
}
