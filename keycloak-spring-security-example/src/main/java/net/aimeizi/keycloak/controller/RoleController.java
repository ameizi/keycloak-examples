package net.aimeizi.keycloak.controller;

import net.aimeizi.keycloak.service.RoleService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleRepresentation> findAll() {
        return roleService.findAll();
    }

    @PostMapping
    public void create(@RequestParam String name) {
        roleService.create(name);
    }

}
