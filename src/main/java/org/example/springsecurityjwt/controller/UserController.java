package org.example.springsecurityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize(value = "hasRole('USER')")
public class UserController {

    @GetMapping("/user")
    public String user() {
        return "ONLY USE USER";
    }
}
