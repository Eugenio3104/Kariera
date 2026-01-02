package it.kariera.api.controller;

import it.kariera.api.dto.LoginRequestDTO;
import it.kariera.api.model.User;
import it.kariera.api.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user , HttpSession session){
        try{
            User registredUser = authService.register(user);
            session.setAttribute("user", registredUser);
            return ResponseEntity.ok(registredUser);
         }catch (Exception e){return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO , HttpSession session){
        try{
            User user = authService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
            session.setAttribute("user", user);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Not authenticated");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}
