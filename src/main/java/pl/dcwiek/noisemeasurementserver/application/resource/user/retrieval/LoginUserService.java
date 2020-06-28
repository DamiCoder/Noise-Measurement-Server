package pl.dcwiek.noisemeasurementserver.application.resource.user.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.service.UserService;
import pl.dcwiek.noisemeasurementserver.security.model.UserMapper;
import pl.dcwiek.noisemeasurementserver.security.model.UserModel;

@Service
@Slf4j
public class LoginUserService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public LoginUserService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserModel login(LoginCommand command) throws ServiceException {
        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        log.debug("GetUserService.login method invoked: " + command.toString());

        try {
            UserEntity userEntity = userService.getUserWithoutConfidentialData(command.getUsername(), command.getPassword());
            return userMapper.mapEntityToModel(userEntity);
        } catch (UserCredentialsException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
