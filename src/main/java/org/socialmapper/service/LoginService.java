package org.socialmapper.service;

import org.socialmapper.libs.User;
import org.socialmapper.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginService {
    private final UserRepo userRepo;
    public static boolean isLogged ;
    public LoginService(UserRepo userRepo) {
        isLogged = false;
        this.userRepo = userRepo;
    }

    public boolean login(String username, String  password){
        User user = userRepo.findUserByUsername(username)
                .orElse(new User("", ""));
        byte[] decoded = Base64.getDecoder().decode(user.getPassword());
        if (new String(decoded).equals(password)) {
            isLogged = true;
            return true;
        }
        return false;
    }

    public void register(String username, String password){
        String encodedPass = new String(Base64.getEncoder().encode(password.getBytes()));
        userRepo.save(new User(username,encodedPass));
    }

}
