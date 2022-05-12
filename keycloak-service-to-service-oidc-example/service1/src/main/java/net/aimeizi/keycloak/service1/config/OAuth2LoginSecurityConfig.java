package net.aimeizi.keycloak.service1.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

// Enable Spring Security features
@EnableWebSecurity
// Enable support / interpretation of security annotations, e.g. `@PreAuthorize(...)`
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        // Allow certain endpoints to go unauthenticated
                        .antMatchers("/public/**").permitAll()
                        // The other endpoints have to be authenticated
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
