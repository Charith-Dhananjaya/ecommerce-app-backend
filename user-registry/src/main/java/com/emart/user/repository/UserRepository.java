package com.emart.user.repository;

import com.emart.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User,String> {
    User findByUsername(String username);
}
