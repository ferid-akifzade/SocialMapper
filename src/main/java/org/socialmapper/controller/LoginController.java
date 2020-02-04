package org.socialmapper.controller;

import org.socialmapper.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String getLogin(){
        return "login";
    }

    @PostMapping
    public void postLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        if (loginService.login(username, password))
            response.sendRedirect("/");
        else
            response.sendRedirect("/login");
    }

    @PutMapping
    public void register(@RequestParam String username, @RequestParam String password){
        loginService.register(username,password);
    }

}
