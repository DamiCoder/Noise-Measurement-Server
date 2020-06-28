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
import pl.dcwiek.noisemeasurementserver.domain.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.domain.database.repo.UserRepository;
import pl.dcwiek.noisemeasurementserver.security.model.UserMapper;
import pl.dcwiek.noisemeasurementserver.security.model.UserModel;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final ShaService shaService;
    private final UserMapper userMapper;

    @Autowired
    public AppAuthenticationProvider(UserRepository userRepository, ShaService shaService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.userMapper = userMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        try{
            String encryptedPassword = shaService.hashPassword(authentication.getCredentials().toString());
            log.debug(String.format("encrypted password: %s", encryptedPassword));
            UserEntity dbUser = userRepository.findByUsernameAndPassword(name, encryptedPassword);
            if(dbUser == null) {
                return null;
            } else {
                if(dbUser.getPassword().equals(encryptedPassword)) {
                    List<GrantedAuthority> grantedAuthorities = dbUser.getUserRoles()
                            .stream()
                            .map(it -> new SimpleGrantedAuthority(it.getUserRole().toString()))
                            .collect(Collectors.toList());
                    UserModel user = userMapper.mapEntityToModel(dbUser);
                    return new UsernamePasswordAuthenticationToken(user, encryptedPassword, grantedAuthorities);
                } else {
                    return null;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
