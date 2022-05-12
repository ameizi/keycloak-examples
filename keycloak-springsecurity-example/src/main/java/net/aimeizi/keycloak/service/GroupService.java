package net.aimeizi.keycloak.service;

import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void create(String name) {
        val group = new GroupRepresentation();
        group.setName(name);
        keycloak
                .realm(realm)
                .groups()
                .add(group);
    }

    public List<GroupRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .groups()
                .groups();
    }

}
