package org.example.springsecurityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.example.springsecurityjwt.entity.User;
import org.example.springsecurityjwt.enums.UserRole;
import org.example.springsecurityjwt.repository.UserRepository;
import org.example.springsecurityjwt.request.AuthRequest;
import org.example.springsecurityjwt.response.AuthResponse;
import org.example.springsecurityjwt.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) throws Exception {
        if (this.userRepository.findByUsername(authRequest.getUsername())
                .isPresent()) {
            return ResponseEntity.ok("USERNAME IS ALREADY EXIT");
        }
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(UserRole.USER)
                .build();
        this.userRepository.save(user);
        return ResponseEntity.ok("LOGIN SUCCESSFULLY");
    }

    @PostMapping("/register")
    public ResponseEntity<String> getAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        if (this.userRepository.findByUsername(authRequest.getUsername())
                .isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.ok("USERNAME IS NOT FOUND");
    }
}

