package pl.dcwiek.noisemeasurementserver.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.UserRoleEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtUserDetails implements UserDetails {

    private final UserEntity user;

    public ExtUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoleEntity userRole : user.getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority(userRole.getUserRole().toString()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
