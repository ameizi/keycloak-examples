package net.aimeizi.keycloak.service1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class EndpointController {

    private final WebClient webclient;

    @Autowired
    public EndpointController(final WebClient webclient) {
        this.webclient = webclient;
    }

    // Secured resource
    @PreAuthorize("hasAuthority('SCOPE_service1:resource1')")
    @GetMapping("/resource1")
    public String resource1() {
        return "Service1.Resource1";
    }

    // Secured resource
    @PreAuthorize("hasAuthority('SCOPE_service1:resource2')")
    @GetMapping("/resource2")
    public String resource2() {
        return "Service1.Resource2";
    }

    // Resource for demo and testing purposes only.
    // AuthN and AuthZ not required.
    @GetMapping("/public/jump/{toService}/{toResource}")
    public String jumpEndpoint(@PathVariable("toService") final String toService,
                               @PathVariable("toResource") final String toResource) {

        String service;
        switch (toService) {
            case "service2":
                service = "http://localhost:10002";
                break;
            case "service3":
                service = "http://localhost:10003";
                break;
            default:
                service = "none";
        }

        if ("none".equals(service)) {
            return "Not able to call that service. Try /public/jump/service2/resource1";
        }

        final String calleeUrl = service + "/" + toResource;

        // The below code is no more then just plain HTTP call to a protected service.
        final String response1 = webclient.get().uri(calleeUrl)
                .exchangeToMono(x -> {
                    final int rawStatusCode = x.rawStatusCode();
                    switch (rawStatusCode) {
                        case 401:
                        case 403:
                            return Mono.just("Access denied");
                        case 200:
                            return x.bodyToMono(String.class);
                        default:
                            return Mono.just("Some other error. Status: " + rawStatusCode);
                    }
                }).block();

        return "Service 1 called " + calleeUrl + " and got: " + response1;
    }

}
