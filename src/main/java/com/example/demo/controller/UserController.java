package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/user")
@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("getUser/{id}")
    public String getUser(@PathVariable("id") Long userId, Model model) {
        logger.info("getUser <-- " + userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        logger.info("getUser --> " + user);
        return "getUser";
    }

    @GetMapping("/getUser")
    public String getMainPage(Model model) {
        logger.info("getMainPage started");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            logger.error("getMainPage --> auth is null");
        } else {
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);
            logger.info("getMainPage --> " + user);
        }
        return "getUser";
    }

    @GetMapping("/getAll")
    public String getAllUsers(Model model) {
        logger.info("getAllUsers started");
        List<User> users = userService.getAllUsers();
        model.addAttribute("alluser", users);
        logger.info("getAllUsers --> " + users);
        return "getAllUser";
    }

    @GetMapping("/save")
    public String showFormForCreation() {
        return "add-user";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .address(userDto.getAddress())
                .password(userDto.getPassword())
                .roles(userDto.getRoles().stream().map(roleName -> roleService.findByName(roleName).orElseThrow()).collect(Collectors.toSet()))
        .build();
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showFormForEdit(@PathVariable Long id, Model model) {
        logger.info("showFormForEdit <-- " + id);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        logger.info("showFormForEdit --> " + user);
        return "user-form";
    }

    @PostMapping("/edit")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUserByUserid(id);
        return "redirect:/";
    }
}



