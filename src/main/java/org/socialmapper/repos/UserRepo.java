package org.socialmapper.repos;

import org.socialmapper.libs.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
}
