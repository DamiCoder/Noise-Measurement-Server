package pl.dcwiek.noisemeasurementserver.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.database.repository.UserEntityRepository;
import pl.dcwiek.noisemeasurementserver.security.model.ExtUserDetails;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new ExtUserDetails(user);
    }
}
