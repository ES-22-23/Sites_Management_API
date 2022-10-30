package es.module2.smapi.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableWebSecurity
@KeycloakConfiguration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {

        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();

        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);

    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/newOwner").hasRole("admin")
                .antMatchers("/getOwner").hasAnyRole("user", "admin")
                .antMatchers("/updateOwner").hasAnyRole("user", "admin")
                .antMatchers("/deleteOwner").hasRole("admin")
                .antMatchers("/newProperty").hasRole("admin")
                .antMatchers("/getProperty").hasRole("admin")
                .antMatchers("/updateProperty").hasRole("admin")
                .antMatchers("/deleteProperty").hasRole("admin")
                .antMatchers("/newAlarm").hasRole("admin")
                .antMatchers("/getAlarm").hasRole("admin")
                .antMatchers("/updateAlarm").hasRole("admin")
                .antMatchers("/deleteAlarm").hasRole("admin")
                .antMatchers("/newCamera").hasRole("admin")
                .antMatchers("/getCamera").hasRole("admin")
                .antMatchers("/updateCamera").hasRole("admin")
                .antMatchers("/deleteCamera").hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
