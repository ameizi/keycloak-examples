package net.aimeizi.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;

import static java.util.Collections.singletonList;

public class KeycloakAdminClientTest {

    private static final String role = "role_admin";

    private static final String adminName = "admin";
    private static final String adminPassword = "admin";
    private static final String realmAdmin = "master";
    private static final String adminClientId = "admin-cli";
    private static final String authServerUrl = "http://192.168.201.188:8080/auth";
    private static final String realm = "my-realm";

    public static void main(String[] args) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(realmAdmin)
                .clientId(adminClientId)
                .username(adminName).password(adminPassword)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
        // 遍历 realm
        keycloak.realms().findAll().forEach(realm -> {
            System.out.println("realm.getRealm() = " + realm.getRealm());
        });

        // 定义用户
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername("184675420@qq.com");
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("184675420@qq.com");

        // 获取RealmResource
        RealmResource realmResource = keycloak.realm(realm);
        // 获取UsersResource
        UsersResource usersResource = realmResource.users();

        // 创建用户
        Response response = usersResource.create(user);
        System.out.println("response.getStatus() = " + response.getStatus());
        System.out.println(response.getStatusInfo().toString());

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            // 非临时密码
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue("123456");

            // 获取用户
            UserResource userResource = usersResource.get(userId);
            if (userResource != null) {
                // 修改密码
                userResource.resetPassword(passwordCred);
            }

            // 定义角色
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(role);

            // 创建角色
            RolesResource rolesResource = realmResource.roles();
            rolesResource.create(roleRepresentation);

            // 获取角色
            roleRepresentation = rolesResource.get(role).toRepresentation();

            // 分配角色
            assert userResource != null;
            userResource.roles().realmLevel().add(singletonList(roleRepresentation));

            // 删除角色
            rolesResource.get(role).remove();
            // 删除用户
            userResource.remove();
        }
    }
}
