package com.example.demo.service;

import com.example.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
    List<Role> findAll();
}
