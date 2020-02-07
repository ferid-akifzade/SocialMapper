package org.socialmapper.service;

import org.socialmapper.libs.User;
import org.socialmapper.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembersService {

    private final UserRepo userRepo;

    public MembersService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUserList() {
        return (List<User>) userRepo.findAll();
    }
}
