package it.kariera.api.controller;

import it.kariera.api.dto.LoginRequestDTO;
import it.kariera.api.model.User;
import it.kariera.api.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        try{
            authService.register(user);
            return ResponseEntity.ok("registered successfully");
         }catch (Exception e){return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO , HttpSession session){
        try{
            User user = authService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
            session.setAttribute("user", user);
            return ResponseEntity.ok("log in successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
