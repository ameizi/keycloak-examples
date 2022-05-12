package net.aimeizi.keycloak.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class KeycloakConfig {

    /**
     * 注入keycloakConfigResolver，让Spring Boot从application properties/yaml 中读取Keycloak配置，而不是从默认的类路径的keycloak.json中读取配置。
     *
     * @return
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * 自己写代码解析keycloak.json
     *
     * @return
     */
    // @Bean
    public KeycloakConfigResolver fileKeycloakConfigResolver() {
        return request -> {
            // json 文件放到resources 文件夹下
            ClassPathResource classPathResource = new ClassPathResource("./keycloak.json");
            AdapterConfig adapterConfig = null;
            try {
                adapterConfig = new ObjectMapper().readValue(classPathResource.getFile(), AdapterConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return KeycloakDeploymentBuilder.build(adapterConfig);
        };
    }

}
