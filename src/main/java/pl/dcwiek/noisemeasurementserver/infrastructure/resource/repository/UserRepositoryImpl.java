package pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.UsernameAlreadyExistsException;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.UserRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.mapper.UserMapper;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;

import javax.transaction.Transactional;

@Service
class UserRepositoryImpl implements UserRepository {

    private final UserEntityRepository userEntityRepository;
    private final UserRoleEntityRepository userRoleEntityRepository;
    private final ShaService shaService;

    @Autowired
    UserRepositoryImpl(UserEntityRepository userEntityRepository,
                       UserRoleEntityRepository userRoleEntityRepository,
                       ShaService shaService) {
        this.userEntityRepository = userEntityRepository;
        this.userRoleEntityRepository = userRoleEntityRepository;
        this.shaService = shaService;
    }

    @Override
    public AppUser getUser(String username, String password) throws NoSuchUserException {
        String hashedPassword = shaService.hashPassword(password);
        UserEntity user = userEntityRepository.findByUsernameAndPassword(username, hashedPassword);
        if (user == null) {
            throw new NoSuchUserException("Wrong credentials or user doesn't exist");
        }
        return UserMapper.mapEntityToModel(user);
    }

    @Override
    @Transactional
    public AppUser createUser(String username, String password) throws UsernameAlreadyExistsException {
        if (userEntityRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        } else {
            String hashedPassword = shaService.hashPassword(password);
            UserEntity user = new UserEntity(0, username, hashedPassword, userRoleEntityRepository.findByUserRole(UserRole.USER), true);
            user = userEntityRepository.save(user);
            return UserMapper.mapEntityToModel(user);
        }
    }

    @Override
    @Transactional
    public void updateAppUser(AppUser appUser) throws NoSuchUserException {
        UserEntity user = userEntityRepository.findById(appUser.getId()).orElse(null);
        if(user == null) {
            throw new NoSuchUserException("No user exists with given ID");
        }
        user.setCreatedDate(appUser.getCreatedDate());
        user.setFirstLogIn(appUser.isFirstLogIn());
        user.setUserRoles(appUser.getUserRoles());
        userEntityRepository.save(user);
    }
}
