package org.socialmapper.service;

import org.socialmapper.libs.Target;
import org.socialmapper.repos.TargetRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultsService {
    private final TargetRepo targetRepo;

    public ResultsService(TargetRepo targetRepo) {
        this.targetRepo = targetRepo;
    }

    public List<Target> getTargetList(){
        return (List<Target>) targetRepo.findAll();
    }
}
