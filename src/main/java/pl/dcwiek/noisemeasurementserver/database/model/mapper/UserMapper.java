package pl.dcwiek.noisemeasurementserver.database.model.mapper;

import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;

public class UserMapper {

    public static AppUser mapEntityToModel(UserEntity userEntity) {
        return new AppUser(userEntity.getId(), userEntity.getUsername(), userEntity.getCreatedDate(), userEntity.getUserRoles());
    }
}
