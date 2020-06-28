package pl.dcwiek.noisemeasurementserver.security.model;

import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;

@Component
public class UserMapper {

    public UserModel mapEntityToModel(UserEntity userEntity) {
        return new UserModel(userEntity.getId(), userEntity.getUsername(), userEntity.getCreatedDate(), userEntity.getUserRoles());
    }
}
