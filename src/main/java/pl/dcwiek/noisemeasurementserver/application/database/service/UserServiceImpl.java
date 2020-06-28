package pl.dcwiek.noisemeasurementserver.application.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.application.resource.user.UserMapper;
import pl.dcwiek.noisemeasurementserver.application.resource.user.UserModel;
import pl.dcwiek.noisemeasurementserver.domain.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.domain.database.repo.UserRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.repo.UserRoleRepository;
import pl.dcwiek.noisemeasurementserver.domain.database.service.UserService;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;

import java.security.NoSuchAlgorithmException;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ShaService shaService;
    private final UserMapper userMapper;

    @Autowired
    UserServiceImpl(UserRepository userRepository,
                    UserRoleRepository userRoleRepository,
                    ShaService shaService,
                    UserMapper userMapper){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.shaService = shaService;
        this.userMapper = userMapper;
    }


    @Override
    public UserEntity getUserWithoutConfidentialData(String username, String password)
            throws ServiceException, UserCredentialsException {
        try{
            String hashedPassword = shaService.hashPassword(password);
            UserEntity user = userRepository.findByUsernameAndPassword(username, hashedPassword);
            if (user == null) {
                throw new UserCredentialsException("Wrong credentials or user doesn't exist");
            }
            return user.withoutConfidentialData();
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public UserModel createUser(String username, String password) throws ServiceException, UsernameAlreadyExistsException {
        try{
            if(userRepository.existsByUsername(username)){
                throw new UsernameAlreadyExistsException();
            } else {
                String hashedPassword = shaService.hashPassword(password);
                UserEntity user = new UserEntity(0, username, hashedPassword, userRoleRepository.findByUserRole(UserRole.USER));
                user = userRepository.save(user);
                return userMapper.mapEntityToModel(user);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}
