package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final UserRepository userRepository;
    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute("user")User user) {
        userRepository.save(user);
        return "redirect:/login";
    }
}
