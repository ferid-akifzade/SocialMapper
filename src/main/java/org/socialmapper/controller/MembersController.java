package org.socialmapper.controller;

import org.socialmapper.libs.User;
import org.socialmapper.service.LoginService;
import org.socialmapper.service.MembersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping("/members")
    public String getMembers(Model model, HttpServletResponse response) throws IOException {
        List<User> userList = membersService.getUserList();
        model.addAttribute("users", userList);
        if (!LoginService.isLogged) {
            response.sendRedirect("/login");
        }
        return "members";
    }

}
