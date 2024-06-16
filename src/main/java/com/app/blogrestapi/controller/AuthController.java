package com.app.blogrestapi.controller;

import com.app.blogrestapi.dto.JWTAuthResponse;
import com.app.blogrestapi.dto.LoginDTO;
import com.app.blogrestapi.dto.RegisterDTO;
import com.app.blogrestapi.model.User;
import com.app.blogrestapi.service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
//    @PostMapping(value = {"/login", "/signin"})
//    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO){
//        logger.debug("Received login request for username: {}", loginDTO.getUsernameOrEmail());
//        try{
//
//            String token = authService.login(loginDTO);
//
//            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//            jwtAuthResponse.setAccessToken(token);
//
//            logger.debug("Login successfully for username: {}", loginDTO.getUsernameOrEmail());
//            return ResponseEntity.ok(jwtAuthResponse);
//        }catch (AuthenticationException e){
//
//            logger.warn("Login failed for username: {}", loginDTO.getUsernameOrEmail());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }catch (Exception e) {
//
//            logger.error("An error during login for username: {}", loginDTO.getUsernameOrEmail());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @GetMapping("/Hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/test")
    public ResponseEntity<List<User>> getUserTest(){
        List<User> findUser = authService.getUserTest();
        return new ResponseEntity<>(findUser, HttpStatus.OK);
    }
}
