package com.app.blogrestapi.service.impl;

import com.app.blogrestapi.controller.AuthController;
import com.app.blogrestapi.dto.LoginDTO;
import com.app.blogrestapi.dto.RegisterDTO;
import com.app.blogrestapi.model.Role;
import com.app.blogrestapi.model.User;
import com.app.blogrestapi.exception.BlogAPIException;
import com.app.blogrestapi.repository.RoleRepository;
import com.app.blogrestapi.repository.UserRepository;
import com.app.blogrestapi.security.JwtTokenProvider;
import com.app.blogrestapi.service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Override
//    public String login(LoginDTO loginDTO){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDTO.getUsernameOrEmail(), loginDTO.getPassword()
//        ));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return jwtTokenProvider.generateToken(authentication);
//    }

    @Override
    public String login(LoginDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDTO registerDTO){

        // add check for username exists in database
        if (userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exist!.");
        }

        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exist!");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
//        Role userRole = roleRepository.findByName("USER").get();

        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() -> new BlogAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found."));

//        roles.add(userRole);

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new BlogAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found."));
        if (registerDTO.isAdmin()){
            roles.add(adminRole);
        }else {
            roles.add(userRole);
        }

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }

    public List<User> getUserTest(){
        List<User> user = userRepository.findAll();
        return user;
    }
}
