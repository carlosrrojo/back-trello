package com.carlos.trello.persistence.repo;

import com.carlos.trello.persistence.model.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {
    Optional<CustomUser> findByUsername(String username);
}
