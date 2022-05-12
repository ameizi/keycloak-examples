package net.aimeizi.keycloak.controller;

import net.aimeizi.keycloak.service.GroupService;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupRepresentation> findAll() {
        return groupService.findAll();
    }

    @PostMapping
    public void create(@RequestParam String name) {
        groupService.create(name);
    }

}
