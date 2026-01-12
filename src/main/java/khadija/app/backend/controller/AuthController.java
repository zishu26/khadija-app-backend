package khadija.app.backend.controller;

import khadija.app.backend.dto.LoginRequest;
import khadija.app.backend.security.JwtUtil;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Don't return authentication object, just generate tokens
        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        // String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());

        return Map.of(
                "access_token", accessToken
                // "refresh_token", refreshToken
        );
    }
}
