package com.angecure.api_users.service;

import com.angecure.api_users.model.Users;
import com.angecure.api_users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Users> getUser(Integer id) {
        return userRepository.findById(id);
    }
}