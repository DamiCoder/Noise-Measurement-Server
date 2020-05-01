package pl.dcwiek.noisemeasurementserver.security.authentication;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.dcwiek.noisemeasurementserver.model.User;
import pl.dcwiek.noisemeasurementserver.repo.UserRepository;
import pl.dcwiek.noisemeasurementserver.security.service.ShaService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AppAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;
    private ShaService shaService;

    @Autowired
    public AppAuthenticationProvider(UserRepository userRepository, ShaService shaService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        try{
            String encryptedPassword = shaService.hashPassword(authentication.getCredentials().toString());
            log.debug(String.format("encrypted password: %s", encryptedPassword));
            User user = userRepository.findByUsernameAndPassword(name, encryptedPassword);
            if(user == null) {
                return null;
            } else {
                if(user.getPassword().equals(encryptedPassword)) {
                    List<GrantedAuthority> grantedAuthorities = user.getUserRoles()
                            .stream()
                            .map(it -> new SimpleGrantedAuthority(it.getApplicationUserRole().toString()))
                            .collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(name, encryptedPassword, grantedAuthorities);
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
