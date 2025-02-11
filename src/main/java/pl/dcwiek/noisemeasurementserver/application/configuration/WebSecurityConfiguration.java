package pl.dcwiek.noisemeasurementserver.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.AccessDeniedHandler;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.AppAuthenticationProvider;


@Configuration
@EnableJpaRepositories
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("pl.dcwiek.noisemeasurementserver.infrastructure.security")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppAuthenticationProvider appAuthenticationProvider;

    private final BasicAuthEntryPoint basicAuthEntryPoint;

    @Autowired
    public WebSecurityConfiguration(AppAuthenticationProvider appAuthenticationProvider,
                                    BasicAuthEntryPoint basicAuthEntryPoint) {
        this.appAuthenticationProvider = appAuthenticationProvider;
        this.basicAuthEntryPoint = basicAuthEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(appAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers("api/public/**").permitAll()
                .antMatchers("api/**").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler())
                .and()
                .csrf().disable();
    }
}
