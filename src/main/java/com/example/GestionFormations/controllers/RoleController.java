package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.RoleEntity;
import com.example.GestionFormations.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<RoleEntity> getRoles(){
        return roleService.getRoles();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "{roleId}")
    RoleEntity getRole(@PathVariable Long roleId) {
        return roleService.getRole(roleId);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void registerNewRole(@Valid @RequestBody RoleEntity role, BindingResult bindingResult){
        roleService.addNewRole(role,bindingResult);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{roleId}")
    public void updateRole(
            @PathVariable("roleId") Long roleId,
            @RequestBody RoleEntity roleUpdate
    ){
        roleService.updateRole(roleId, roleUpdate);

    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteRoleById(@PathVariable Long id) {
        roleService.DeleteRole(id);
    }

    @GetMapping(path="/link/{userId}/{roleId}")
    public void linkUserToRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId){
        roleService.linkNewUserToRole(userId, roleId);
    }
}