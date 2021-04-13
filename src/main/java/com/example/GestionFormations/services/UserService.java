package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.UserEntity;
import com.example.GestionFormations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service(value = "userService")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByLogin(username).get();
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUser(Long id) {
        UserEntity user = getUsers().stream().filter(t -> id.equals(t.getCode()))
                .findFirst()
                .orElse(null);
        return user;

    }

    public void addNewUser(UserEntity user, BindingResult bindingResult) {
        Optional<UserEntity> userOptional = userRepository.findUserEntityByLogin(user.getLogin());
        String password=user.getPassword();
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(pw_hash);
        if (userOptional.isPresent()){
            throw new IllegalStateException("login taken");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        userRepository.save(user);
    }

    public void DeleteUser(Long id){
        Optional<UserEntity> userId = userRepository.findById(id);
        if(!userId.isPresent()){
            throw new IllegalStateException("user does not exist");
        }
        userId.get().setRoles(null);
        userRepository.deleteById(id);
    }

    public void updateUser(Long userId, UserEntity userUpdate) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException(
                "user with id " + userId + " does not exist"));

        if (userUpdate.getPassword()!=null &&
                userUpdate.getPassword().length() > 0 &&
                !Objects.equals(user.getPassword(), userUpdate.getPassword())){
            String pw_hash = BCrypt.hashpw(userUpdate.getPassword(), BCrypt.gensalt(10));
            user.setPassword(pw_hash);
        }

        if (userUpdate.getLogin()!=null &&
                userUpdate.getLogin().length() > 0 &&
                !Objects.equals(user.getLogin(), userUpdate.getLogin())){
            user.setLogin(userUpdate.getLogin());
        }

        if (userUpdate.getRoles()!=null &&
                userUpdate.getRoles().toString().length() > 0 &&
                !Objects.equals(user.getRoles(), userUpdate.getRoles())){
            user.setRoles(userUpdate.getRoles());
        }

        userRepository.save(user);

    }
}
