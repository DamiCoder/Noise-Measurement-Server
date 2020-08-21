package pl.dcwiek.noisemeasurementserver.application.resource.user.creation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.UserRepository;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

@Service
@Slf4j
public class CreateUserService {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(CreateUserCommand command) throws ServiceException {
        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        log.info("CreateUserService.createUser method invoked: " + command);

        try {
            return userRepository.createUser(command.getUsername(), command.getPassword());
        } catch (UsernameAlreadyExistsException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
