package org.socialmapper.controller;

import org.socialmapper.libs.Target;
import org.socialmapper.service.LoginService;
import org.socialmapper.service.ResultsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultsController {

    private final ResultsService resultsService;

    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }


    @GetMapping
    public String getMembers(Model model, HttpServletResponse response) throws IOException {
        List<Target> targetList = resultsService.getTargetList();
        model.addAttribute("targets", targetList);
        if (!LoginService.isLogged) {
            response.sendRedirect("/login");
        }
        return "results";
    }
}
