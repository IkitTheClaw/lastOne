package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequestMapping("/user")
@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }


    @GetMapping("/getUser")
    public String getMainPage(Model model) {
        logger.info("getMainPage started");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth == null) {
            //logger.error("getMainPage --> auth is null");
            System.out.println("1");
        } else {
            user = (User) auth.getPrincipal();
            model.addAttribute("user", user);
            logger.info("getMainPage --> " + user);
        }
        return "getUser";
    }



//    @GetMapping("/save")
//    public String showFormForCreation() {
//        return "add-user";
//    }
//
//    @PostMapping("/save")
//    public String addUser(@ModelAttribute("user") UserDto userDto) {
//        logger.info("addUser <-- " + userDto);
//        User user = userMapper.toUser(userDto);
//        userService.save(user);
//        logger.info("addUser --> " + user);
//        return "redirect:/";
//    }

    @GetMapping("/edit/{id}")
    public String showMyFormForEdit(@PathVariable Long id, Model model) {
        logger.info("showFormForEdit <-- " + id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
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
        return "user-form";
    }

    @PostMapping("/edit/{id}")
    public String editMyInfo(@PathVariable Long id, @ModelAttribute("user") UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank() ) {
            user.setPassword(userDto.getPassword());
        }
        userService.save(user);
        return "redirect:/user/getUser";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUserByUserid(id);
        return "redirect:/login";
    }
}



