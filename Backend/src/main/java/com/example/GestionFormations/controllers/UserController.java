package com.example.GestionFormations.controllers;

import com.example.GestionFormations.services.UserService;
import com.example.GestionFormations.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "{userId}")
    UserEntity getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/signup")
    public UserEntity registerNewUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult){
        userService.addNewUser(user,bindingResult);
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteUserById(@PathVariable Long id) {
        userService.DeleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserEntity userUpdate
    ){
        userService.updateUser(userId, userUpdate);

    }
}
