package com.angecure.api_users.repository;

import com.angecure.api_users.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Integer> {
}