package pl.dcwiek.noisemeasurementserver.security;

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
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final ShaService shaService;

    @Autowired
    public AppAuthenticationProvider(UserRepository userRepository,
                                     ShaService shaService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String encryptedPassword = shaService.hashPassword(authentication.getCredentials().toString());
        log.debug(String.format("encrypted password: %s", encryptedPassword));
        AppUser user;
        try {
            user = userRepository.getUser(name, encryptedPassword);
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
            return new UsernamePasswordAuthenticationToken(user, encryptedPassword, grantedAuthorities);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
