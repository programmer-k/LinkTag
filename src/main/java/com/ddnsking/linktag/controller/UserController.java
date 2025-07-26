package com.ddnsking.linktag.controller;

import com.ddnsking.linktag.dto.CreateUserRequest;
import com.ddnsking.linktag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String showCreateUserForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return "redirect:/login";
    }
}
