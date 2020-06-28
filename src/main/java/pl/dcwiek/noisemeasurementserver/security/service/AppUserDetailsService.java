package pl.dcwiek.noisemeasurementserver.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.repo.UserRepository;
import pl.dcwiek.noisemeasurementserver.security.model.AppUserPrincipal;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new AppUserPrincipal(user);
    }
}
