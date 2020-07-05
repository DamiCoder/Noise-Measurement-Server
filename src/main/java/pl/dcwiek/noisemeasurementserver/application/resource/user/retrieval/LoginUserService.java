package pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.application.resource.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.UserRepository;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;

@Service
@Slf4j
public class LoginUserService {

    private final UserRepository userRepository;

    @Autowired
    public LoginUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser login(LoginCommand command) throws ServiceException {
        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        log.debug("GetUserService.login method invoked: " + command.toString());

        try {
            return userRepository.getUser(command.getUsername(), command.getPassword());
        } catch (NoSuchUserException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
