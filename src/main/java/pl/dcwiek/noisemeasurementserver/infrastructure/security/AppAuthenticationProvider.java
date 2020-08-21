package pl.dcwiek.noisemeasurementserver.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.UserRepository;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AppUser;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Autowired
    public AppAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        AppUser user;
        try {
            user = userRepository.getUser(name, password);
        } catch (NoSuchUserException e) {
            return null;
        }
        if (user == null) {
            return null;
        } else {
            List<GrantedAuthority> grantedAuthorities = user.getUserRoles()
                    .stream()
                    .map(it -> new SimpleGrantedAuthority(it.getUserRole().toString()))
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(user, password, grantedAuthorities);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
