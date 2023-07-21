package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    User save(User user);

    void deleteUserByUserid(long id);

}

