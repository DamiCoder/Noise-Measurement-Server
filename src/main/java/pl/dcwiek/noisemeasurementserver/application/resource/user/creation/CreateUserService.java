package pl.dcwiek.noisemeasurementserver.application.resource.user.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.domain.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.database.service.UserService;
import pl.dcwiek.noisemeasurementserver.security.model.UserModel;

@Component
public class CreateUserService {

    private final UserService userService;

    @Autowired
    public CreateUserService(UserService userService) {
        this.userService = userService;
    }

    public UserModel createUser(CreateUserCommand command) throws ServiceException {
        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        try {
            UserModel user = userService.createUser(command.getUsername(), command.getPassword());
            return user;
        } catch (UsernameAlreadyExistsException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
