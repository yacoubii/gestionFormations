package com.example.GestionFormations.controllers;


import com.example.GestionFormations.config.JwtTokenUtil;
import com.example.GestionFormations.entities.AuthToken;
import com.example.GestionFormations.entities.UserEntity;
import com.example.GestionFormations.repositories.UserRepository;
import com.example.GestionFormations.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserEntity loginUser) throws AuthenticationException {
        UserEntity user = userRepository.findUserEntityByLogin(loginUser.getLogin()).get();
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getLogin(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(authentication);
        AuthToken returnedToken=new AuthToken(token);
        JSONObject obj=new JSONObject();
        obj.put("id",user.getCode());
        obj.put("username",user.getLogin());
        obj.put("roles",user.getRoles());
        obj.put("tokenType","Bearer");
        obj.put("token",returnedToken.getToken());
        return ResponseEntity.ok(obj);
    }

}
