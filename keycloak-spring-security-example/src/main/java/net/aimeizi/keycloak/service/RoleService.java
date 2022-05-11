package net.aimeizi.keycloak.service;

import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void create(String name) {
        val role = new RoleRepresentation();
        role.setName(name);
        keycloak
                .realm(realm)
                .roles()
                .create(role);
    }

    public List<RoleRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .roles()
                .list();
    }

    public RoleRepresentation findByName(String roleName) {
        return keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();
    }
}
