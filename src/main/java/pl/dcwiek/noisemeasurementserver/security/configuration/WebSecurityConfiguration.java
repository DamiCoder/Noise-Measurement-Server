package pl.dcwiek.noisemeasurementserver.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.dcwiek.noisemeasurementserver.security.AccessDeniedHandler;
import pl.dcwiek.noisemeasurementserver.security.AppAuthenticationProvider;


@Configuration
@EnableJpaRepositories
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("pl.dcwiek.noisemeasurementserver.security")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppAuthenticationProvider appAuthenticationProvider;

    @Autowired
    public WebSecurityConfiguration(AppAuthenticationProvider appAuthenticationProvider) {
        this.appAuthenticationProvider = appAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(appAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("api/public/**").permitAll()
                .antMatchers("api/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler())
                .and()
                .csrf().disable();
    }
}
