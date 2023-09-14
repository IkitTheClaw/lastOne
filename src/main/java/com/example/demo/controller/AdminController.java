package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
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
import java.util.Set;
import java.util.stream.Collectors;


@RequestMapping("/admin")
@Controller
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @GetMapping("getUser/{id}")
    public String getUser(@PathVariable("id") Long userId, Model model) {
        logger.info("getUser <-- " + userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        logger.info("getUser --> " + user);
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
        logger.info("addUser <-- " + userDto);
        User user = userMapper.toUser(userDto);
        userService.save(user);
        logger.info("addUser --> " + user);
        return "redirect:/admin/getAll";
    }

    @GetMapping("/edit/{id}")
    public String showFormForEdit(@PathVariable Long id, Model model) {
        logger.info("showFormForEdit <-- " + id);
        User user = userService.getUserById(id);
        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .address(user.getAddress())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .build();
        model.addAttribute("user", userDto);
        model.addAttribute("id",id);
        logger.info("showFormForEdit --> " + userDto);
        return "user-form-admin";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") UserDto userDto) {
        User user = userService.getUserById(id);
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank() ) {
            user.setPassword(userDto.getPassword());
        }
        userService.save(user);
        return "redirect:/admin/getAll";
    }

}
