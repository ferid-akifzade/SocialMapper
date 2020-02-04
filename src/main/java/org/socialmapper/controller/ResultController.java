package org.socialmapper.controller;

import org.socialmapper.libs.Target;
import org.socialmapper.service.LoginService;
import org.socialmapper.service.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultController {

    private final ResultService service;

    public ResultController(ResultService service) {
        this.service = service;
    }

    @RequestMapping
    public String getResult(Model model, HttpServletResponse response) throws IOException {
        if (LoginService.isLogged) {
            List<Target> data = service.getData();
            model.addAttribute("targets", data);
            String error = service.getError();
            if (!error.isEmpty())
                model.addAttribute("errorMessage", error);
            else
                model.addAttribute("errorMessage", "");
        }
        else
            response.sendRedirect("/login");

        return "results";
    }

}
