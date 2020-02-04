package org.socialmapper.service;

import lombok.extern.slf4j.Slf4j;
import org.socialmapper.libs.Const;
import org.socialmapper.libs.Target;
import org.socialmapper.libs.User;
import org.socialmapper.repos.TargetRepo;
import org.socialmapper.repos.UserRepo;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MapperService {
    private final UserRepo userRepo;
    private final TargetRepo targetRepo;
    private final List<User> usersData;
    private String error = "";

    public MapperService(UserRepo userRepo, TargetRepo targetRepo) {
        this.targetRepo = targetRepo;
        usersData = new LinkedList<>();
        usersData.add(new User(0, "test", "test"));
        usersData.add(new User(1, "test2", "test2"));
        usersData.add(new User(2, "test3", "test3"));
        this.userRepo = userRepo;
    }

    public String render(Model model, String action, String doAction, int id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(String.format("src/main/resources/templates/%s.html", action))));
            String collect = reader.lines().collect(Collectors.joining());
            reader.close();
            if (action.equals("members")) {
                if (doAction.equals("delete") && id != -1) {
                    delete(id);
                }
                model.addAttribute("users", usersData);
                return "members";
            } else {
                model.addAttribute("content", collect);
                return "index";
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
            model.addAttribute("content", "");
            return "index";
        }
    }

    public void search(String name, String surname, MultipartFile file, String[] actions, String mode) throws IOException {
        //-fb -tw -ig -li -vk -db -wb
        StringBuilder stringBuilder = new StringBuilder();
        for (String action : actions) {
            switch (action) {
                case "all":
                    stringBuilder.append(" -a");
                    break;
                case "facebook":
                    stringBuilder.append(" -fb");
                    break;
                case "twitter":
                    stringBuilder.append(" -tw");
                    break;
                case "instagram":
                    stringBuilder.append(" -ig");
                    break;
                case "linkedin":
                    stringBuilder.append(" -li");
                    break;
                case "vkontakte":
                    stringBuilder.append(" -vk");
                    break;
            }
        }
        createSearchRequest(name, surname, file, stringBuilder.toString(), mode);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(50));
        factory.setMaxRequestSize(DataSize.ofMegabytes(70));
        return factory.createMultipartConfig();
    }
    private void setError(String error){
        this.error = error;
    }
    public String getError(){
        return error;
    }

    private void createSearchRequest(String name, String surname, MultipartFile imageFile, String doAction, String mode) throws IOException {
        if ("accurate".equals(mode) || "fast".equals(mode)) {
            File resultImage = new File(String.format("social_mapper/target/%s %s.jpg", name, surname));
            FileOutputStream fileOutputStream = new FileOutputStream(resultImage);
            fileOutputStream.write(imageFile.getBytes());
            fileOutputStream.close();
            String format = String.format("python3 social_mapper/social_mapper.py -f imagefolder -i social_mapper/target -m %s %s", mode, doAction);
            Process mapping = Runtime.getRuntime().exec(format);
            BufferedReader mappingResult = new BufferedReader(new InputStreamReader(mapping.getInputStream()));
            List<String> result = mappingResult.lines().collect(Collectors.toList());
            parseResult(name, surname, result);
            resultImage.delete();
        }
    }

    private void parseResult(String name, String surname, List<String> mappingResults) {
        Set<Target> targets = new HashSet<>();
        Target target = new Target();
        mappingResults.stream()
                .map(String::trim)
                .forEach(s -> {
                    if (s.contains(Const.fb)) {
                        target.setFacebook(s.substring(Const.fb.length()).trim());
                    }
                    if (s.contains(Const.tw)) {
                        target.setTwitter(s.substring(Const.tw.length()).trim());
                    }
                    if (s.contains(Const.in)) {
                        target.setInstagram(s.substring(Const.in.length()).trim());
                    }
                    if (s.contains(Const.lk)) {
                        target.setLinkedin(s.substring(Const.lk.length()).trim());
                    }
                    if (s.contains(Const.vk)) {
                        target.setVkontakte(s.substring(Const.vk.length()).trim());
                    }
                    if (s.equals("")) {
                        if (target.isNotEmpty()) {
                            target.setName(name);
                            target.setSurname(surname);
                            targets.add(new Target(target));
                        }
                        target.clear();
                    }
                    if(s.contains("Failed")){
                        setError(String.join(",",error,s));
                    }
                });

        targetRepo.saveAll(new ArrayList<>(targets));
    }

    public void delete(int id) {
        usersData.forEach(user -> {
            if (user.getId() == id)
                usersData.remove(user);
        });
    }
}
