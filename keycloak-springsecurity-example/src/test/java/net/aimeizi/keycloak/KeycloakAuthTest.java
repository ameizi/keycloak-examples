package net.aimeizi.keycloak;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Keycloak登陆认证测试
 */
public class KeycloakAuthTest {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String authServerUrl = "http://192.168.201.188:8080/auth";
    private static String authTokenUrl = "http://192.168.201.188:8080/auth/realms/my-realm/protocol/openid-connect/token";
    private static String realm = "my-realm";
    private static String clientId = "spring-boot-security-bearer";
    private static String clientSecret = "2ae3a35b-983a-42ff-bf6d-06a7268ca940";
    private static String username = "sfeng";
    private static String password = "123456";

    public static void main(String[] args) throws Exception {
        invoke1();
        invoke2();
        invoke();
    }

    private static void invoke2() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", OAuth2Constants.PASSWORD);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(authTokenUrl, HttpMethod.POST, entity, String.class);
        AccessTokenResponse accessTokenResponse = objectMapper.readValue(response.getBody(), AccessTokenResponse.class);
        System.out.println(JSONUtil.toJsonStr(accessTokenResponse));
        HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/admin");
        // HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/principal");
        request.header("Authorization", "Bearer " + accessTokenResponse.getToken());
        HttpResponse resp = request.send();
        System.out.println(resp.bodyText());
    }

    private static void invoke1() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", OAuth2Constants.PASSWORD);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("username", username);
        map.put("password", password);
        HttpResponse response = HttpRequest.post(authTokenUrl).header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE).form(map).send();
        AccessTokenResponse accessTokenResponse = objectMapper.readValue(response.bodyText(), AccessTokenResponse.class);
        System.out.println(JSONUtil.toJsonStr(accessTokenResponse));
        HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/admin");
        // HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/principal");
        request.header("Authorization", "Bearer " + accessTokenResponse.getToken());
        HttpResponse resp = request.send();
        System.out.println(resp.bodyText());
    }

    private static void invoke() {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", OAuth2Constants.PASSWORD);
        Configuration configuration = new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = authzClient.obtainAccessToken(username, password);
        System.out.println("response.getToken() = " + response.getToken());
        // HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/user");
        HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/admin");
        // HttpRequest request = HttpRequest.get("http://172.24.107.84:8080/principal");
        request.header("Authorization", "Bearer " + response.getToken());
        HttpResponse resp = request.send();
        System.out.println(resp.bodyText());
    }
}
