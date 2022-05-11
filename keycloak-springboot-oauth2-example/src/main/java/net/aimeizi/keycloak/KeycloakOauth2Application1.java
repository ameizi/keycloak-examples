package net.aimeizi.keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class KeycloakOauth2Application1 {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KeycloakOauth2Application1.class);
        application.setAdditionalProfiles("app1");
        application.run(args);
    }

    @GetMapping(value = "/app1/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object principal(@AuthenticationPrincipal Jwt principal) {
        System.out.println(principal.getClaimAsString("preferred_username"));
        return principal;
    }

}
