package org.example.springsecurityjwt.config;

import lombok.RequiredArgsConstructor;
import org.example.springsecurityjwt.entity.User;
import org.example.springsecurityjwt.enums.UserRole;
import org.example.springsecurityjwt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class FakeUserSave {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //@Bean
    public CommandLineRunner runUser() {
        return a -> {
            User admin = User.builder()
                    .password(passwordEncoder.encode("admin"))
                    .username("admin")
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(admin);

            User user = User.builder()
                    .password(passwordEncoder.encode("user"))
                    .username("user")
                    .role(UserRole.USER)
                    .build();
            userRepository.save(user);
        };
    }
}
