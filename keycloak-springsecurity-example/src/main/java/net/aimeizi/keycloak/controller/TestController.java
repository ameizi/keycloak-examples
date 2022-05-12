package net.aimeizi.keycloak.controller;

import net.aimeizi.keycloak.service.TestService;
import net.aimeizi.keycloak.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody UserVo userCreateReq) {
        return ResponseEntity.ok(testService.createUser(userCreateReq));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody UserVo userVo) {
        return ResponseEntity.ok(testService.login(userVo));
    }

    @GetMapping(value = "/unprotected")
    public String getUnProtectedData() {
        return "This api is not protected.";
    }

    @GetMapping(value = "/protected")
    public String getProtectedData() {
        return "This api is protected.";
    }

}
