package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.RoleEntity;
import com.example.GestionFormations.entities.UserEntity;
import com.example.GestionFormations.repositories.RoleRepository;
import com.example.GestionFormations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    public List<RoleEntity> getRoles(){
        return roleRepository.findAll();
    }

    public RoleEntity getRole(Long id) {
        RoleEntity role = getRoles().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return role;
    }

    public void addNewRole(RoleEntity role, BindingResult bindingResult) {
        Optional<RoleEntity> roleOptional = roleRepository.findRoleEntityByName(role.getName());
        if (roleOptional.isPresent()){
            throw new IllegalStateException("role already exists");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        roleRepository.save(role);
    }

    public void DeleteRole(Long id){
        Optional<RoleEntity> roleId = roleRepository.findById(id);
        if(!roleId.isPresent()){
            throw new IllegalStateException("role does not exist");
        }
        roleRepository.deleteById(id);
    }

    public void updateRole(Long roleId, RoleEntity roleUpdate) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(()-> new IllegalStateException(
                "role with id " + roleId + " does not exist"));
        if (roleUpdate.getName() !=null &&
                roleUpdate.getName().toString().length() > 0 &&
                !Objects.equals(role.getName(), roleUpdate.getName())){
            role.setName(roleUpdate.getName());
        }
        roleRepository.save(role);
    }

    @Transactional
    public void linkNewUserToRole(Long userId, Long roleId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException(
                "user with id " + userId + " does not exist"));
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(()-> new IllegalStateException(
                "role with id " + roleId + " does not exist"));

        Set<RoleEntity> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        //userRepository.save(user);
    }
}