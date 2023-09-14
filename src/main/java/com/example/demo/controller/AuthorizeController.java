package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final UserService userService;
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
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
    public String registrationSubmit(@ModelAttribute("user") UserDto userDto) {
        logger.info("registrationSubmit <-- " + userDto);
        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .address(userDto.getAddress())
                .password(userDto.getPassword())
                .build();
        Set<Role> roles = userDto.getRoles()
                .stream()
                .map(roleName -> roleService.findByName(roleName).orElseThrow())
                .peek(System.out::println)
                .collect(Collectors.toSet());
        logger.info("registrationSubmit --> Roles:" + roles.stream().map(it -> it.toString()).collect(Collectors.joining(", ")));
        user.setRoles(roles);
        userService.save(user);
        logger.info("registrationSubmit --> " + user);
        return "redirect:/login";
    }
}
