package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;


@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userinfo/{id}")
    public String getUser(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "getUser";
    }

    @GetMapping("/userinfo/all")
    public String getAllUser(Model model) {
        model.addAttribute("alluser", userService.getAllUsers());
        return "getAllUser";
    }

    @GetMapping("/userinfo/add")
    public String showFormForCreation(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "add-user";
    }

    @PostMapping("/userinfo/save")
    public String addUser(@ModelAttribute("user") @Validated User user, BindingResult result) {
        if (result.hasErrors()){
            return "add-user";
        }
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showFormForEdit(@PathVariable Long id,Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "user-form";
    }
    @PostMapping("/edit/update")
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



