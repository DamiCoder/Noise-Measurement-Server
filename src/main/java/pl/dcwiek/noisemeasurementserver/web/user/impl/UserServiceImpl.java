package pl.dcwiek.noisemeasurementserver.web.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.User;
import pl.dcwiek.noisemeasurementserver.database.repo.UserRepository;
import pl.dcwiek.noisemeasurementserver.security.model.UserCredentials;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;
import pl.dcwiek.noisemeasurementserver.web.user.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.web.user.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.web.user.model.UserRegistrationForm;
import pl.dcwiek.noisemeasurementserver.web.user.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.web.user.service.UserService;
import pl.dcwiek.noisemeasurementserver.web.user.service.mapper.UserMapper;

import java.security.NoSuchAlgorithmException;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShaService shaService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ShaService shaService, UserMapper userMapper){
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.userMapper = userMapper;
    }


    @Override
    public User getUserWithoutConfidentialData(UserCredentials userCredentials)
            throws ServiceException, UserCredentialsException {
        try{
            String hashedPassword = shaService.hashPassword(userCredentials.getPassword());
            User user = userRepository.findByUsernameAndPassword(userCredentials.getUsername(), hashedPassword);
            if (user == null) {
                throw new UserCredentialsException("Wrong credentials or user doesn't exist");
            }
            return user.withoutConfidentialData();
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public User createUser(UserRegistrationForm userRegistrationForm)
            throws ServiceException, UsernameAlreadyExistsException {
        try{
            if(userRepository.existsByUsername(userRegistrationForm.getUsername())){
                throw new UsernameAlreadyExistsException();
            } else {
                String hashedPassword = shaService.hashPassword(userRegistrationForm.getPassword());
                UserCredentials userCredentials = new UserCredentials(
                        userRegistrationForm.getUsername(),
                        hashedPassword);
                User user = userMapper.mapCredentialsToRegularUser(userCredentials);
                User savedUser = userRepository.save(user);
                return savedUser.withoutConfidentialData();
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}
