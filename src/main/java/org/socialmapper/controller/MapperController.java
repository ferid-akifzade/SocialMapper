package org.socialmapper.controller;

import lombok.extern.slf4j.Slf4j;
import org.socialmapper.service.LoginService;
import org.socialmapper.service.MapperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
@Slf4j
public class MapperController {
    private final MapperService service;

    public MapperController(MapperService service) {
        this.service = service;
    }

    @GetMapping
    public String getDefault(@RequestParam(value = "act", defaultValue = "search", required = false) String action,
                             @RequestParam(value = "do", defaultValue = "", required = false) String doAction,
                             @RequestParam(value = "id", defaultValue = "-1", required = false) int id,
                             HttpServletResponse response,
                             Model model) throws IOException {
        if (LoginService.isLogged)
            return service.render(model, action, doAction, id);
        else
            response.sendRedirect("/login");
            return "results";
    }

    @PostMapping
    public void postDefault(@RequestParam(value = "imageFile") MultipartFile image,
                              @RequestParam(value = "mode", defaultValue = "fast") String mode,
                              @RequestParam(value = "name", defaultValue = "def") String name,
                              @RequestParam(value = "surname", defaultValue = "def") String surname,
                              @RequestParam(value = "social") String[] socials,
                              HttpServletResponse response) throws IOException {
        if (LoginService.isLogged) {

            service.search(name, surname, image, socials, mode);
            response.sendRedirect("/results");
        }
        else {
            response.sendRedirect("/login");
        }
    }
}
