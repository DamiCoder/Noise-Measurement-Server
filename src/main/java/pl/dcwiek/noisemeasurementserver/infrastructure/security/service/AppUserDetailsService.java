package pl.dcwiek.noisemeasurementserver.infrastructure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.resource.repository.UserEntityRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.ExtUserDetails;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Autowired
    public AppUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new ExtUserDetails(user);
    }
}
