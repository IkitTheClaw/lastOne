package com.example.demo.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private String username;
    private String name;
    private String surname;
    private String address;
    private String password;
    private List<String> roles;
}
