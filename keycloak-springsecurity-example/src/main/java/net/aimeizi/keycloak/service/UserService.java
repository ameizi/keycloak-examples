package net.aimeizi.keycloak.service;

import lombok.val;
import net.aimeizi.keycloak.vo.UserRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Collections.singletonList;


@Service
public class UserService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public List<UserRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .users()
                .list();
    }

    public List<UserRepresentation> findByUsername(String username) {
        return keycloak
                .realm(realm)
                .users()
                .search(username);
    }

    public UserRepresentation findById(String id) {
        return keycloak
                .realm(realm)
                .users()
                .get(id)
                .toRepresentation();
    }

    public void assignToGroup(String userId, String groupId) {
        keycloak
                .realm(realm)
                .users()
                .get(userId)
                .joinGroup(groupId);
    }

    public void assignRole(String userId, RoleRepresentation roleRepresentation) {
        keycloak
                .realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(singletonList(roleRepresentation));
    }

    public Response create(UserRequest request) {
        val password = preparePasswordRepresentation(request.getPassword());
        val user = prepareUserRepresentation(request, password);
        return keycloak
                .realm(realm)
                .users()
                .create(user);
    }

    private UserRepresentation prepareUserRepresentation(UserRequest request, CredentialRepresentation password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setCredentials(singletonList(password));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }


}
