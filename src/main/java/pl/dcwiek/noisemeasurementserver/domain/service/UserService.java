package pl.dcwiek.noisemeasurementserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.UserRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUserLogInInfo(AppUser appUser) throws ServiceException {
        AppUser updatedAppUser = new AppUser(appUser.getId(), appUser.getUsername(), appUser.getCreatedDate(), false, appUser.getUserRoles());
        try {
            userRepository.updateAppUser(updatedAppUser);
        } catch (NoSuchUserException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
