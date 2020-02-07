package org.socialmapper.controller;

import org.socialmapper.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class AddMemberController {

    @RequestMapping("/addmember")
    public String members(HttpServletResponse response) throws IOException {
        if (!LoginService.isLogged) {
            response.sendRedirect("/login");
        }
        return "addmember";
    }

}
