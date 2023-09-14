package com.example.demo.mapper;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final RoleService roleService;

    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .address(userDto.getAddress())
                .password(userDto.getPassword())
                .roles(userDto.getRoles()
                        .stream()
                        .map(roleName -> roleService.findByName(roleName).orElseThrow())
                        .peek(System.out::println)
                        .collect(Collectors.toSet()))
                .build();

    }
}
