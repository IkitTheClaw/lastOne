package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
        User findUserByUserId(Long userId);
        User findUserByUsername(String username);

        List<User> findAll();

        User save(User user);

        void deleteUserByUserId(long id);



}
