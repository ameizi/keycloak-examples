package net.aimeizi.keycloak;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;

import java.util.HashMap;
import java.util.Map;

public class weibo {
    public static void main(String[] args) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", "08b0a2c2-42c0-4cbe-8453-1cd8f9f9063c");
        clientCredentials.put("grant_type", OAuth2Constants.PASSWORD);
        String authServerUrl = "http://192.168.201.188:8080/auth";
        String realm = "my-realm";
        String clientId = "weibo-app";
        String username = "sfeng";
        String password = "123456";
        Configuration configuration = new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = authzClient.obtainAccessToken(username, password);
        System.out.println("response.getToken() = " + response.getToken());
        // HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/user");
        HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/admin");
        request.header("Authorization", "Bearer " + response.getToken());
        HttpResponse resp = request.send();
        System.out.println(resp.bodyText());
    }
}
