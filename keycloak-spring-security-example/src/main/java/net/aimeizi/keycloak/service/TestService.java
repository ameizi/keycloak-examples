package net.aimeizi.keycloak.service;

import net.aimeizi.keycloak.vo.UserVo;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

@Service
public class TestService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private static final String role = "role_user";
    private static final String adminName = "admin";
    private static final String adminPassword = "admin";
    private static final String realmAdmin = "master";
    private static final String adminClientId = "admin-cli";

    /**
     * 使用master realm为realm创建用户及分配角色，并且设置密码
     *
     * @param userVo
     * @return
     */
    public UserVo createUser(UserVo userVo) {
        // 此处使用的是master realm下的管理员账号密码
        Keycloak keycloak = KeycloakBuilder.builder().
                serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).
                realm(realmAdmin).
                clientId(adminClientId)
                .username(adminName).password(adminPassword)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
        // 定义用户
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userVo.getEmail());
        user.setFirstName(userVo.getFirstname());
        user.setLastName(userVo.getLastname());
        user.setEmail(userVo.getEmail());

        // 获取RealmResource
        RealmResource realmResource = keycloak.realm(realm);
        // 获取UsersResource
        UsersResource usersResource = realmResource.users();

        // 创建用户
        Response response = usersResource.create(user);
        userVo.setStatusCode(response.getStatus());
        userVo.setStatus(response.getStatusInfo().toString());

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            // 非临时密码
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userVo.getPassword());

            // 获取用户
            UserResource userResource = usersResource.get(userId);
            // 修改密码
            userResource.resetPassword(passwordCred);

            // 定义角色
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(role);
            // 创建角色
            realmResource.roles().create(roleRepresentation);
            // 获取角色
            roleRepresentation = realmResource.roles().get(role).toRepresentation();
            System.out.println("roleId: " + roleRepresentation.getId());
            // 分配角色
            userResource.roles().realmLevel().add(singletonList(roleRepresentation));
        }
        return userVo;
    }

    /**
     * 用户登陆，主要测试用新创建的用户登陆
     *
     * @param userVo
     * @return
     */
    public Object login(UserVo userVo) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");
        Configuration configuration = new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = authzClient.obtainAccessToken(userVo.getEmail(), userVo.getPassword());
        return ResponseEntity.ok(response);
    }
}
