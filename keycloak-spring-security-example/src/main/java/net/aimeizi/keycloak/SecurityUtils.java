package net.aimeizi.keycloak;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public final class SecurityUtils {
    /**
     * 返回Keycloak security context，其中包含当前登录的Keycloak User的详细信息
     *
     * @return
     */
    public static IDToken getIDToken() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final Principal principal = (Principal) authentication.getPrincipal();
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            return keycloakPrincipal.getKeycloakSecurityContext().getIdToken();
        }
        return null;
    }
}